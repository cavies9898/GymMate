package com.gymmate.app.domain.model

data class Exercise(
    val id: Long = 0,
    val name: String,
    val description: String,
    val muscleGroup: MuscleGroup,
    val workoutType: WorkoutType,
    val difficulty: DifficultyLevel,
    val durationSeconds: Int?,
    val reps: Int?,
    val sets: Int?,
    val imageUrl: String? = null,
    val animationAsset: String? = null
)