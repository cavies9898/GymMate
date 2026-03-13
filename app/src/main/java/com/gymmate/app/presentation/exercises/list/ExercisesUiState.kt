package com.gymmate.app.presentation.exercises.list

import com.gymmate.app.domain.model.DifficultyLevel
import com.gymmate.app.domain.model.Exercise
import com.gymmate.app.domain.model.MuscleGroup
import com.gymmate.app.domain.model.WorkoutType

data class ExercisesUiState(
    val isLoading: Boolean = true,
    val allExercises: List<Exercise> = emptyList(),
    val filteredExercises: List<Exercise> = emptyList(),
    val selectedWorkoutType: WorkoutType = WorkoutType.HOME,
    val selectedMuscleGroup: MuscleGroup? = null,
    val selectedDifficulty: DifficultyLevel? = null,
    val searchQuery: String = "",
    val error: String? = null
)