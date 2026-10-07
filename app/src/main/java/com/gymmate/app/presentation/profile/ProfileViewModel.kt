package com.gymmate.app.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gymmate.app.domain.model.DifficultyLevel
import com.gymmate.app.domain.model.FitnessGoal
import com.gymmate.app.domain.model.UserProfile
import com.gymmate.app.domain.model.WorkoutType
import com.gymmate.app.domain.usecase.profile.GetProfileDataUseCase
import com.gymmate.app.domain.usecase.profile.ProfileData
import com.gymmate.app.domain.usecase.profile.SaveUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileDataUseCase: GetProfileDataUseCase,
    private val saveUserProfileUseCase: SaveUserProfileUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        observeProfileData()
    }

    private fun observeProfileData() {
        viewModelScope.launch {
            getProfileDataUseCase()
                .collect { data: ProfileData ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        profile = data.profile,
                        totalSessions = data.totalSessions,
                        activeStreak = data.activeStreak,
                        totalMinutes = data.totalMinutes,
                        favoriteRoutineName = data.favoriteRoutineName,
                        editName = data.profile?.name ?: "",
                        editWeight = data.profile?.weight,
                        editHeight = data.profile?.height,
                        editGoal = data.profile?.fitnessGoal ?: FitnessGoal.STAY_ACTIVE,
                        editExperience = data.profile?.experienceLevel ?: DifficultyLevel.BEGINNER
                    )
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
}