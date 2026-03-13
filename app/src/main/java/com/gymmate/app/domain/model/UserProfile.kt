package com.gymmate.app.domain.model

data class UserProfile(
    val id: Long = 0,
    val name: String,
    val fitnessGoal: FitnessGoal,
    val preferredWorkoutType: WorkoutType,
    val weeklyGoalDays: Int,
    val weight: Float? = null,
    val height: Float? = null,
    val experienceLevel: DifficultyLevel = DifficultyLevel.BEGINNER
)