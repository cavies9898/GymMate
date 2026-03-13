package com.gymmate.app.presentation.profile

import com.gymmate.app.domain.model.FitnessGoal
import com.gymmate.app.domain.model.DifficultyLevel
import com.gymmate.app.domain.model.UserProfile

data class ProfileUiState(
    val isLoading: Boolean = true,
    val profile: UserProfile? = null,
    val totalSessions: Int = 0,
    val activeStreak: Int = 0,
    val totalMinutes: Int = 0,
    val favoriteRoutineName: String? = null,
    val showEditModal: Boolean = false,
    // campos editables temporales
    val editName: String = "",
    val editWeight: Float? = null,
    val editHeight: Float? = null,
    val editGoal: FitnessGoal = FitnessGoal.STAY_ACTIVE,
    val editExperience: DifficultyLevel = DifficultyLevel.BEGINNER
)