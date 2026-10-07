package com.gymmate.app.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.gymmate.app.domain.model.DifficultyLevel
import com.gymmate.app.domain.model.MuscleGroup

// Theme-aware color getters
@Composable
fun getDifficultyColor(level: com.gymmate.app.domain.model.DifficultyLevel): Color {
    val isDark = MaterialTheme.colorScheme.background.isDark()
    return when (level) {
        com.gymmate.app.domain.model.DifficultyLevel.BEGINNER -> if (isDark) DarkDifficultyBeginner else LightDifficultyBeginner
        com.gymmate.app.domain.model.DifficultyLevel.INTERMEDIATE -> if (isDark) DarkDifficultyIntermediate else LightDifficultyIntermediate
        com.gymmate.app.domain.model.DifficultyLevel.ADVANCED -> if (isDark) DarkDifficultyAdvanced else LightDifficultyAdvanced
    }
}

@Composable
fun getMuscleGroupColor(group: com.gymmate.app.domain.model.MuscleGroup): Color {
    val isDark = MaterialTheme.colorScheme.background.isDark()
    return when (group) {
        com.gymmate.app.domain.model.MuscleGroup.CHEST -> if (isDark) DarkMuscleChest else LightMuscleChest
        com.gymmate.app.domain.model.MuscleGroup.BACK -> if (isDark) DarkMuscleBack else LightMuscleBack
        com.gymmate.app.domain.model.MuscleGroup.SHOULDERS -> if (isDark) DarkMuscleShoulders else LightMuscleShoulders
        com.gymmate.app.domain.model.MuscleGroup.BICEPS -> if (isDark) DarkMuscleBiceps else LightMuscleBiceps
        com.gymmate.app.domain.model.MuscleGroup.TRICEPS -> if (isDark) DarkMuscleTriceps else LightMuscleTriceps
        com.gymmate.app.domain.model.MuscleGroup.CORE -> if (isDark) DarkMuscleCore else LightMuscleCore
        com.gymmate.app.domain.model.MuscleGroup.GLUTES -> if (isDark) DarkMuscleGlutes else LightMuscleGlutes
        com.gymmate.app.domain.model.MuscleGroup.LEGS -> if (isDark) DarkMuscleLegs else LightMuscleLegs
        com.gymmate.app.domain.model.MuscleGroup.FULL_BODY -> if (isDark) DarkMuscleFullBody else LightMuscleFullBody
        com.gymmate.app.domain.model.MuscleGroup.CARDIO -> if (isDark) DarkMuscleCardio else LightMuscleCardio
    }
}

@Composable
fun getConfettiColors(): List<Color> {
    val isDark = MaterialTheme.colorScheme.background.isDark()
    return if (isDark) DarkConfettiColors else LightConfettiColors
}