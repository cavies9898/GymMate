package com.gymmate.app.presentation.exercises.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gymmate.app.domain.model.DifficultyLevel
import com.gymmate.app.domain.model.Exercise
import com.gymmate.app.domain.model.MuscleGroup
import com.gymmate.app.domain.model.WorkoutType
import com.gymmate.app.domain.usecase.exercise.ExerciseFilters
import com.gymmate.app.domain.usecase.exercise.GetFilteredExercisesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ExercisesViewModel @Inject constructor(
    private val getFilteredExercisesUseCase: GetFilteredExercisesUseCase
) : ViewModel() {

    private val _filters = MutableStateFlow(
        ExerciseFilters(
            workoutType = WorkoutType.HOME,
            muscleGroup = null,
            difficulty = null,
            searchQuery = ""
        )
    )
    private val _uiState = MutableStateFlow(ExercisesUiState())
    val uiState: StateFlow<ExercisesUiState> = _uiState.asStateFlow()

    init {
        observeFilteredExercises()
    }

    private fun observeFilteredExercises() {
        viewModelScope.launch {
            _filters
                .flatMapLatest { filters ->
                    getFilteredExercisesUseCase(filters)
                }
                .distinctUntilChanged()
                .collect { exercises ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        filteredExercises = exercises,
                        selectedWorkoutType = _filters.value.workoutType,
                        selectedMuscleGroup = _filters.value.muscleGroup,
                        selectedDifficulty = _filters.value.difficulty
                    )
                }
        }
    }

    fun toggleWorkoutType() {
        val current = _filters.value.workoutType
        val newWorkoutType = if (current == WorkoutType.HOME) WorkoutType.GYM else WorkoutType.HOME
        _filters.value = _filters.value.copy(
            workoutType = newWorkoutType,
            muscleGroup = null,
            difficulty = null
        )
        _uiState.value = _uiState.value.copy(
            selectedWorkoutType = newWorkoutType,
            selectedMuscleGroup = null,
            selectedDifficulty = null
        )
    }

    fun selectMuscleGroup(muscleGroup: MuscleGroup?) {
        _filters.value = _filters.value.copy(muscleGroup = muscleGroup)
    }

    fun selectDifficulty(difficulty: DifficultyLevel?) {
        _filters.value = _filters.value.copy(difficulty = difficulty)
    }

    fun onSearchQueryChange(query: String) {
        _filters.value = _filters.value.copy(searchQuery = query)
    }
}