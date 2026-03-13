package com.gymmate.app.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gymmate.app.domain.model.WorkoutType
import com.gymmate.app.domain.usecase.profile.GetUserProfileUseCase
import com.gymmate.app.domain.usecase.routine.GetAllRoutinesUseCase
import com.gymmate.app.domain.usecase.workout.GetAllWorkoutSessionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllRoutinesUseCase: GetAllRoutinesUseCase,
    private val getAllWorkoutSessionsUseCase: GetAllWorkoutSessionsUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadHomeData()
    }

    fun toggleWorkoutType() {
        val current = _uiState.value.selectedWorkoutType
        _uiState.value = _uiState.value.copy(
            selectedWorkoutType = if (current == WorkoutType.HOME) WorkoutType.GYM else WorkoutType.HOME
        )
    }

    private fun loadHomeData() {
        viewModelScope.launch {
            combine(
                getAllRoutinesUseCase(),
                getAllWorkoutSessionsUseCase(),
                getUserProfileUseCase()
            ) { routines, sessions, profile ->
                val completedSessions = sessions.filter { it.completed }
                HomeUiState(
                    isLoading = false,
                    userName = profile?.name ?: "Atleta",
                    completedSessions = completedSessions.size,
                    activeStreak = calculateStreak(completedSessions.map { it.startedAt }),
                    featuredRoutines = routines.take(6),
                    lastSession = completedSessions.maxByOrNull { it.startedAt }
                )
            }.collect { state ->
                _uiState.value = state.copy(
                    selectedWorkoutType = _uiState.value.selectedWorkoutType
                )
            }
        }
    }

    private fun calculateStreak(dates: List<LocalDateTime>): Int {
        if (dates.isEmpty()) return 0
        val uniqueDays = dates.map { it.toLocalDate() }.toSortedSet().toList().sortedDescending()
        var streak = 0
        var expected = LocalDate.now()
        for (day in uniqueDays) {
            if (day == expected) {
                streak++
                expected = expected.minusDays(1)
            } else break
        }
        return streak
    }
}