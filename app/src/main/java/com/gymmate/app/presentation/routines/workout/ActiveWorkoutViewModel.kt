package com.gymmate.app.presentation.routines.workout

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gymmate.app.domain.model.WorkoutSession
import com.gymmate.app.domain.service.VibrationService
import com.gymmate.app.domain.usecase.routine.GetRoutineDetailUseCase
import com.gymmate.app.domain.usecase.workout.SaveWorkoutSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ActiveWorkoutViewModel @Inject constructor(
    private val getRoutineDetailUseCase: GetRoutineDetailUseCase,
    private val saveWorkoutSessionUseCase: SaveWorkoutSessionUseCase,
    private val vibrationService: VibrationService,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(ActiveWorkoutUiState())
    val uiState: StateFlow<ActiveWorkoutUiState> = _uiState.asStateFlow()

    private var restTimerJob: Job? = null
    private var elapsedTimerJob: Job? = null
    private val startTime = LocalDateTime.now()
    private val routineId: Long = savedStateHandle.get<Long>("routineId") ?: 0L

    init {
        loadRoutine()
        startElapsedTimer()
    }

    private fun loadRoutine() {
        viewModelScope.launch {
            getRoutineDetailUseCase(routineId)
                .collect { routine ->
                    if (routine != null) {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            routineName = routine.name,
                            exercises = routine.exercises.sortedBy { it.order }
                        )
                    }
                }
        }
    }

    fun onNextPressed() {
        val state = _uiState.value
        val exercise = state.currentExercise ?: return
        vibrationService.vibrate()

        if (state.isLastSet && state.isLastExercise) {
            finishWorkout()
            return
        }

        if (state.isLastSet) {
            _uiState.value = _uiState.value.copy(
                currentExerciseIndex = state.currentExerciseIndex + 1,
                currentSet = 1,
                totalSetsCompleted = state.totalSetsCompleted + 1
            )
            startRestTimer(exercise.restSeconds)
        } else {
            _uiState.value = _uiState.value.copy(
                currentSet = state.currentSet + 1,
                totalSetsCompleted = state.totalSetsCompleted + 1
            )
            startRestTimer(exercise.restSeconds)
        }
    }

    fun skipRest() {
        restTimerJob?.cancel()
        _uiState.value = _uiState.value.copy(
            phase = WorkoutPhase.EXERCISE,
            restSecondsRemaining = 0
        )
    }

    private fun startRestTimer(seconds: Int) {
        restTimerJob?.cancel()
        _uiState.value = _uiState.value.copy(
            phase = WorkoutPhase.REST,
            restSecondsRemaining = seconds
        )
        restTimerJob = viewModelScope.launch {
            var remaining = seconds
            while (remaining > 0) {
                delay(1000)
                remaining--
                _uiState.value = _uiState.value.copy(restSecondsRemaining = remaining)
            }
            _uiState.value = _uiState.value.copy(
                phase = WorkoutPhase.EXERCISE,
                restSecondsRemaining = 0
            )
            vibrationService.vibrate()
        }
    }

    private fun startElapsedTimer() {
        elapsedTimerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                _uiState.value = _uiState.value.copy(
                    elapsedSeconds = _uiState.value.elapsedSeconds + 1
                )
            }
        }
    }

    private fun finishWorkout() {
        elapsedTimerJob?.cancel()
        restTimerJob?.cancel()
        _uiState.value = _uiState.value.copy(
            phase = WorkoutPhase.FINISHED,
            totalSetsCompleted = _uiState.value.totalSetsCompleted + 1
        )
        viewModelScope.launch {
            val currentRoutine = getRoutineDetailUseCase(routineId).firstOrNull() ?: return@launch
            saveWorkoutSessionUseCase(
                WorkoutSession(
                    id = 0,
                    routine = currentRoutine,
                    startedAt = startTime,
                    completedAt = LocalDateTime.now(),
                    durationMinutes = (_uiState.value.elapsedSeconds / 60).toInt(),
                    completed = true
                )
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        restTimerJob?.cancel()
        elapsedTimerJob?.cancel()
    }
}