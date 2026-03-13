package com.gymmate.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.gymmate.app.presentation.exercises.detail.ExerciseDetailScreen
import com.gymmate.app.presentation.exercises.list.ExercisesScreen
import com.gymmate.app.presentation.home.HomeScreen
import com.gymmate.app.presentation.profile.ProfileScreen
import com.gymmate.app.presentation.routines.detail.RoutineDetailScreen
import com.gymmate.app.presentation.routines.list.RoutinesScreen
import com.gymmate.app.presentation.routines.workout.ActiveWorkoutScreen
import com.gymmate.app.presentation.routines.workout.WorkoutSummaryScreen
import com.gymmate.app.presentation.splash.SplashScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = AppDestinations.Home.route,
        modifier = modifier
    ) {
        composable(AppDestinations.Splash.route) {
            SplashScreen(navController)
        }

        // --- Bottom Nav ---
        composable(AppDestinations.Home.route) {
            HomeScreen(navController)
        }
        composable(AppDestinations.Routines.route) {
            RoutinesScreen(navController)
        }
        composable(AppDestinations.Exercises.route) {
            ExercisesScreen(navController)
        }
        composable(AppDestinations.Profile.route) {
            ProfileScreen()
        }

        // --- Inner screens ---
        composable(
            route = AppDestinations.RoutineDetail.route,
            arguments = listOf(navArgument("routineId") { type = NavType.LongType })
        ) {
            RoutineDetailScreen(navController)
        }
        composable(
            route = AppDestinations.ActiveWorkout.route,
            arguments = listOf(navArgument("routineId") { type = NavType.LongType })
        ) {
            ActiveWorkoutScreen(navController)
        }
        composable(
            route = AppDestinations.WorkoutSummary.route,
            arguments = listOf(
                navArgument("routineName") { type = NavType.StringType },
                navArgument("durationSeconds") { type = NavType.LongType },
                navArgument("setsCompleted") { type = NavType.IntType },
                navArgument("exercisesCompleted") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val routineName = backStackEntry.arguments?.getString("routineName") ?: ""
            val durationSeconds = backStackEntry.arguments?.getLong("durationSeconds") ?: 0L
            val setsCompleted = backStackEntry.arguments?.getInt("setsCompleted") ?: 0
            val exercisesCompleted = backStackEntry.arguments?.getInt("exercisesCompleted") ?: 0
            WorkoutSummaryScreen(
                navController = navController,
                routineName = routineName,
                durationSeconds = durationSeconds,
                setsCompleted = setsCompleted,
                exercisesCompleted = exercisesCompleted
            )
        }
        composable(
            route = AppDestinations.ExerciseDetail.route,
            arguments = listOf(navArgument("exerciseId") { type = NavType.LongType })
        ) {
            ExerciseDetailScreen(navController)
        }
    }
}