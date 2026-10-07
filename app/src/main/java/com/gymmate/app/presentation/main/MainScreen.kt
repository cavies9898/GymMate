package com.gymmate.app.presentation.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gymmate.app.domain.model.ThemeMode
import com.gymmate.app.presentation.navigation.AppDestinations
import com.gymmate.app.presentation.navigation.AppNavGraph
import com.gymmate.app.presentation.navigation.BottomNavItem
import com.gymmate.app.presentation.theme.GymMateTheme
import androidx.compose.ui.platform.LocalContext

// Rutas donde NO se muestra el Bottom Nav
private val routesWithoutBottomNav = listOf(
    AppDestinations.Splash.route,
    AppDestinations.RoutineDetail.route,
    AppDestinations.ActiveWorkout.route,
    AppDestinations.WorkoutSummary.route,
    AppDestinations.ExerciseDetail.route,
)

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = routesWithoutBottomNav.none { currentRoute?.startsWith(it.substringBefore("{")) == true }

    // Usar el tema del sistema por defecto
    val darkTheme = androidx.compose.foundation.isSystemInDarkTheme()

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    BottomNavItem.items.forEach { item ->
                        NavigationBarItem(
                            selected = currentRoute == item.route,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(AppDestinations.Home.route) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        GymMateTheme(darkTheme = darkTheme) {
            AppNavGraph(
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}