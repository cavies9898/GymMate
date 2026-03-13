package com.gymmate.app.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SportsMartialArts
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    data object Home : BottomNavItem(
        route = AppDestinations.Home.route,
        label = "Home",
        icon = Icons.Default.Home
    )
    data object Rutinas : BottomNavItem(
        route = AppDestinations.Routines.route,
        label = "Rutinas",
        icon = Icons.Default.FitnessCenter
    )
    data object Ejercicios : BottomNavItem(
        route = AppDestinations.Exercises.route,
        label = "Ejercicios",
        icon = Icons.Default.SportsMartialArts
    )
    data object Perfil : BottomNavItem(
        route = AppDestinations.Profile.route,
        label = "Perfil",
        icon = Icons.Default.Person
    )

    companion object {
        val items = listOf(Home, Rutinas, Ejercicios, Perfil)
    }
}