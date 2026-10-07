@file:JvmName("ModelExtensions")

package com.gymmate.app.presentation.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.gymmate.app.R
import com.gymmate.app.domain.model.DifficultyLevel
import com.gymmate.app.domain.model.MuscleGroup
import com.gymmate.app.domain.model.ThemeMode
import com.gymmate.app.domain.model.WorkoutType
import com.gymmate.app.presentation.theme.getDifficultyColor
import com.gymmate.app.presentation.theme.getMuscleGroupColor

@Composable
fun DifficultyLevel.color(): Color = getDifficultyColor(this)

@Composable
fun WorkoutType.gradientColors(): List<Color> = when (this) {
    WorkoutType.HOME -> listOf(
        MaterialTheme.colorScheme.primaryContainer,
        MaterialTheme.colorScheme.primary
    )
    WorkoutType.GYM -> listOf(
        MaterialTheme.colorScheme.secondaryContainer,
        MaterialTheme.colorScheme.secondary
    )
}

@Composable
fun WorkoutType.cardGradientColors(): List<Color> = when (this) {
    WorkoutType.HOME -> listOf(
        MaterialTheme.colorScheme.primaryContainer,
        MaterialTheme.colorScheme.primary
    )
    WorkoutType.GYM -> listOf(
        MaterialTheme.colorScheme.secondaryContainer,
        MaterialTheme.colorScheme.secondary
    )
}

@Composable
fun WorkoutType.emoji(): String = when (this) {
    WorkoutType.HOME -> "🏠"
    WorkoutType.GYM -> "🏋️"
}

@Composable
fun MuscleGroup.color(): Color = getMuscleGroupColor(this)

fun MuscleGroup.icon(): String = when (this) {
    MuscleGroup.CHEST -> "💪"
    MuscleGroup.BACK -> "🏋️"
    MuscleGroup.SHOULDERS -> "🔱"
    MuscleGroup.BICEPS -> "💪"
    MuscleGroup.TRICEPS -> "💪"
    MuscleGroup.CORE -> "🎯"
    MuscleGroup.GLUTES -> "🦵"
    MuscleGroup.LEGS -> "🦵"
    MuscleGroup.FULL_BODY -> "⚡"
    MuscleGroup.CARDIO -> "🫀"
}

fun ThemeMode.displayLabel(): String = when (this) {
    ThemeMode.SYSTEM -> "Sistema"
    ThemeMode.LIGHT -> "Claro"
    ThemeMode.DARK -> "Oscuro"
}