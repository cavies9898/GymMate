package com.gymmate.app.presentation.navigation

object AppRoutes {
    // Root routes
    const val SPLASH = "splash"
    const val ONBOARDING = "onboarding"

    // Bottom navigation routes
    const val HOME = "home"
    const val ROUTINES = "routines"
    const val EXERCISES = "exercises"
    const val PROFILE = "profile"

    // Inner routes with parameters
    const val ROUTINE_DETAIL = "routines/{routineId}"
    const val ACTIVE_WORKOUT = "workout/{routineId}"
    const val WORKOUT_SUMMARY = "workout/summary"
    const val EXERCISE_DETAIL = "exercises/{exerciseId}"

    // Query parameter keys
    const val PARAM_ROUTINE_ID = "routineId"
    const val PARAM_EXERCISE_ID = "exerciseId"
    const val PARAM_ROUTINE_NAME = "routineName"
    const val PARAM_DURATION_SECONDS = "durationSeconds"
    const val PARAM_SETS_COMPLETED = "setsCompleted"
    const val PARAM_EXERCISES_COMPLETED = "exercisesCompleted"

    // Route path segments
    const val PATH_ROUTINES = "routines"
    const val PATH_WORKOUT = "workout"
    const val PATH_SUMMARY = "summary"
    const val PATH_EXERCISES = "exercises"
}