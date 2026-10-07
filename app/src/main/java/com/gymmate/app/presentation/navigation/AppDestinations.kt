package com.gymmate.app.presentation.navigation

import android.net.Uri

sealed class AppDestinations(val route: String) {

    data object Splash : AppDestinations(AppRoutes.SPLASH)
    data object Onboarding : AppDestinations(AppRoutes.ONBOARDING)

    data object Home : AppDestinations(AppRoutes.HOME)
    data object Routines : AppDestinations(AppRoutes.ROUTINES)
    data object Exercises : AppDestinations(AppRoutes.EXERCISES)
    data object Profile : AppDestinations(AppRoutes.PROFILE)

    data object RoutineDetail : AppDestinations(AppRoutes.ROUTINE_DETAIL) {
        fun createRoute(routineId: Long) = "${AppRoutes.PATH_ROUTINES}/$routineId"
        fun parseRoutineId(route: String?): Long? = route?.substringAfterLast("/")?.toLongOrNull()
    }

    data object ActiveWorkout : AppDestinations(AppRoutes.ACTIVE_WORKOUT) {
        fun createRoute(routineId: Long) = "${AppRoutes.PATH_WORKOUT}/$routineId"
        fun parseRoutineId(route: String?): Long? = route?.substringAfterLast("/")?.toLongOrNull()
    }

    data object WorkoutSummary : AppDestinations(AppRoutes.WORKOUT_SUMMARY) {
        fun createRoute(
            routineName: String,
            durationSeconds: Long,
            setsCompleted: Int,
            exercisesCompleted: Int
        ) = "${AppRoutes.WORKOUT_SUMMARY}?" +
                "${AppRoutes.PARAM_ROUTINE_NAME}=$routineName&" +
                "${AppRoutes.PARAM_DURATION_SECONDS}=$durationSeconds&" +
                "${AppRoutes.PARAM_SETS_COMPLETED}=$setsCompleted&" +
                "${AppRoutes.PARAM_EXERCISES_COMPLETED}=$exercisesCompleted"

        fun parseArgs(route: String?): WorkoutSummaryArgs? {
            val uri = route?.let { Uri.parse("myapp://$it") } ?: return null
            val routineName = uri.getQueryParameter(AppRoutes.PARAM_ROUTINE_NAME) ?: return null
            val durationSeconds = uri.getQueryParameter(AppRoutes.PARAM_DURATION_SECONDS)?.toLongOrNull() ?: return null
            val setsCompleted = uri.getQueryParameter(AppRoutes.PARAM_SETS_COMPLETED)?.toIntOrNull() ?: return null
            val exercisesCompleted = uri.getQueryParameter(AppRoutes.PARAM_EXERCISES_COMPLETED)?.toIntOrNull() ?: return null
            return WorkoutSummaryArgs(routineName, durationSeconds, setsCompleted, exercisesCompleted)
        }
    }

    data object ExerciseDetail : AppDestinations(AppRoutes.EXERCISE_DETAIL) {
        fun createRoute(exerciseId: Long) = "${AppRoutes.PATH_EXERCISES}/$exerciseId"
        fun parseExerciseId(route: String?): Long? = route?.substringAfterLast("/")?.toLongOrNull()
    }
}

data class WorkoutSummaryArgs(
    val routineName: String,
    val durationSeconds: Long,
    val setsCompleted: Int,
    val exercisesCompleted: Int
)