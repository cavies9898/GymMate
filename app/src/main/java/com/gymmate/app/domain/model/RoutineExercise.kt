package com.gymmate.app.domain.model

data class RoutineExercise(
    val id: Long = 0,
    val exercise: Exercise,
    val sets: Int,
    val reps: Int?,
    val durationSeconds: Int?,
    val restSeconds: Int,
    val order: Int
)