package com.gymmate.app.presentation.navigation

sealed class AppDestinations(val route: String) {

    data object Splash : AppDestinations("splash")
    data object Onboarding : AppDestinations("onboarding")

    data object Home : AppDestinations("home")
    data object Routines : AppDestinations("routines")
    data object Exercises : AppDestinations("exercises")
    data object Profile : AppDestinations("profile")

    data object RoutineDetail : AppDestinations("routines/{routineId}") {
        fun createRoute(routineId: Long) = "routines/$routineId"
    }
    data object ActiveWorkout : AppDestinations("workout/{routineId}") {
        fun createRoute(routineId: Long) = "workout/$routineId"
    }
    data object WorkoutSummary : AppDestinations("workout/summary/{routineName}/{durationSeconds}/{setsCompleted}/{exercisesCompleted}") {
        fun createRoute(
            routineName: String,
            durationSeconds: Long,
            setsCompleted: Int,
            exercisesCompleted: Int
        ) = "workout/summary/$routineName/$durationSeconds/$setsCompleted/$exercisesCompleted"
    }
    data object ExerciseDetail : AppDestinations("exercises/{exerciseId}") {
        fun createRoute(exerciseId: Long) = "exercises/$exerciseId"
    }
}