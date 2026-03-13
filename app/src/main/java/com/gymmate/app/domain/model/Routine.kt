package com.gymmate.app.domain.model

data class Routine(
    val id: Long = 0,
    val name: String,
    val description: String,
    val workoutType: WorkoutType,
    val difficulty: DifficultyLevel,
    val durationMinutes: Int,
    val exercises: List<RoutineExercise> = emptyList(),
    val imageUrl: String? = null
)