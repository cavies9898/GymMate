package com.gymmate.app.presentation.routines.list

import com.gymmate.app.domain.model.DifficultyLevel
import com.gymmate.app.domain.model.Routine
import com.gymmate.app.domain.model.WorkoutType

enum class SortOrder {
    DEFAULT,
    DURATION_ASC,
    DURATION_DESC
}

data class RoutinesUiState(
    val isLoading: Boolean = true,
    val allRoutines: List<Routine> = emptyList(),
    val filteredRoutines: List<Routine> = emptyList(),
    val selectedWorkoutType: WorkoutType = WorkoutType.HOME,
    val selectedDifficulty: DifficultyLevel? = null,
    val searchQuery: String = "",
    val sortOrder: SortOrder = SortOrder.DEFAULT,
    val error: String? = null
)