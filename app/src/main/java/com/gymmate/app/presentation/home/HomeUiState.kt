package com.gymmate.app.presentation.home

import com.gymmate.app.domain.model.Routine
import com.gymmate.app.domain.model.WorkoutSession
import com.gymmate.app.domain.model.WorkoutType

data class HomeUiState(
    val isLoading: Boolean = true,
    val userName: String = "",
    val completedSessions: Int = 0,
    val activeStreak: Int = 0,
    val featuredRoutines: List<Routine> = emptyList(),
    val lastSession: WorkoutSession? = null,
    val selectedWorkoutType: WorkoutType = WorkoutType.HOME,
    val error: String? = null
)