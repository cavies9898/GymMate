package com.gymmate.app.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gymmate.app.domain.model.DifficultyLevel
import com.gymmate.app.domain.model.FitnessGoal
import com.gymmate.app.domain.model.UserProfile
import com.gymmate.app.domain.model.WorkoutType
import com.gymmate.app.domain.usecase.profile.GetUserProfileUseCase
import com.gymmate.app.domain.usecase.profile.SaveUserProfileUseCase
import com.gymmate.app.domain.usecase.workout.GetAllWorkoutSessionsUseCase
import com.gymmate.app.domain.usecase.routine.GetAllRoutinesUseCase
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
class ProfileViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val saveUserProfileUseCase: SaveUserProfileUseCase,
    private val getAllWorkoutSessionsUseCase: GetAllWorkoutSessionsUseCase,
    private val getAllRoutinesUseCase: GetAllRoutinesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            combine(
                getUserProfileUseCase(),
                getAllWorkoutSessionsUseCase(),
                getAllRoutinesUseCase()
            ) { profile, sessions, routines ->
                val completed = sessions.filter { it.completed }
                val totalMinutes = completed.sumOf { it.durationMinutes }
                val streak = calculateStreak(completed.map { it.startedAt })

                // rutina favorita — la más repetida
                val favoriteRoutineId = completed
                    .groupingBy { it.routine.id }
                    .eachCount()
                    .maxByOrNull { it.value }?.key
                val favoriteRoutineName = routines.find { it.id == favoriteRoutineId }?.name

                ProfileUiState(
                    isLoading = false,
                    profile = profile,
                    totalSessions = completed.size,
                    activeStreak = streak,
                    totalMinutes = totalMinutes,
                    favoriteRoutineName = favoriteRoutineName,
                    editName = profile?.name ?: "",
                    editWeight = profile?.weight,
                    editHeight = profile?.height,
                    editGoal = profile?.fitnessGoal ?: FitnessGoal.STAY_ACTIVE,
                    editExperience = profile?.experienceLevel ?: DifficultyLevel.BEGINNER
                )
            }.collect { state ->
                _uiState.value = state
            }
        }
    }

    fun openEditModal() {
        val state = _uiState.value
        _uiState.value = state.copy(
            showEditModal = true,
            editName = state.profile?.name ?: "",
            editWeight = state.profile?.weight,
            editHeight = state.profile?.height,
            editGoal = state.profile?.fitnessGoal ?: FitnessGoal.STAY_ACTIVE,
            editExperience = state.profile?.experienceLevel ?: DifficultyLevel.BEGINNER
        )
    }

    fun closeEditModal() {
        _uiState.value = _uiState.value.copy(showEditModal = false)
    }

    fun onEditName(name: String) {
        _uiState.value = _uiState.value.copy(editName = name)
    }

    fun onEditWeight(weight: String) {
        _uiState.value = _uiState.value.copy(editWeight = weight.toFloatOrNull())
    }

    fun onEditHeight(height: String) {
        _uiState.value = _uiState.value.copy(editHeight = height.toFloatOrNull())
    }

    fun onEditGoal(goal: FitnessGoal) {
        _uiState.value = _uiState.value.copy(editGoal = goal)
    }

    fun onEditExperience(level: DifficultyLevel) {
        _uiState.value = _uiState.value.copy(editExperience = level)
    }

    fun saveProfile() {
        viewModelScope.launch {
            val state = _uiState.value
            saveUserProfileUseCase(
                UserProfile(
                    name = state.editName.ifBlank { "Atleta" },
                    fitnessGoal = state.editGoal,
                    preferredWorkoutType = state.profile?.preferredWorkoutType ?: WorkoutType.HOME,
                    weeklyGoalDays = state.profile?.weeklyGoalDays ?: 3,
                    weight = state.editWeight,
                    height = state.editHeight,
                    experienceLevel = state.editExperience
                )
            )
            _uiState.value = _uiState.value.copy(showEditModal = false)
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