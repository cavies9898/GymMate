package com.gymmate.app.presentation.theme

import androidx.compose.ui.graphics.Color

// ============================================================
// DARK THEME - Gym Dark (Deep charcoal with energetic accents)
// ============================================================
val DarkPrimary = Color(0xFF00796B)       // Dark teal - navy blue with dark green
val DarkOnPrimary = Color(0xFFFFFFFF)
val DarkPrimaryContainer = Color(0xFF4DB6AC)
val DarkOnPrimaryContainer = Color(0xFF004D40)

val DarkSecondary = Color(0xFF00D4AA)     // Mint green - fresh/progress
val DarkOnSecondary = Color(0xFF001F1A)
val DarkSecondaryContainer = Color(0xFF004D3A)
val DarkOnSecondaryContainer = Color(0xFFA8F8E8)

val DarkTertiary = Color(0xFFFFD600)      // Amber/Gold - achievements
val DarkOnTertiary = Color(0xFF1A1A00)
val DarkTertiaryContainer = Color(0xFF4D3E00)
val DarkOnTertiaryContainer = Color(0xFFFFF8D6)

val DarkError = Color(0xFFFF6E6E)
val DarkOnError = Color(0xFFFFFFFF)
val DarkErrorContainer = Color(0xFF93000A)
val DarkOnErrorContainer = Color(0xFFFFDAD6)

val DarkBackground = Color(0xFF0D0D0D)    // Near black
val DarkOnBackground = Color(0xFFE8E8E8)
val DarkSurface = Color(0xFF1A1A1A)       // Elevated surfaces
val DarkOnSurface = Color(0xFFE8E8E8)
val DarkSurfaceVariant = Color(0xFF2A2A2A)
val DarkOnSurfaceVariant = Color(0xFFB8B8B8)

val DarkOutline = Color(0xFF404040)
val DarkOutlineVariant = Color(0xFF2D2D2D)
val DarkShadow = Color(0xFF000000)
val DarkScrim = Color(0xFF000000)
val DarkInverseSurface = Color(0xFFE8E8E8)
val DarkInverseOnSurface = Color(0xFF1A1A1A)
val DarkInversePrimary = Color(0xFF4DB6AC)

// Dark - Difficulty Levels
val DarkDifficultyBeginner = Color(0xFF4CAF50)   // Green
val DarkDifficultyIntermediate = Color(0xFFFF9800) // Orange
val DarkDifficultyAdvanced = Color(0xFFE53935)   // Red

// Dark - Muscle Groups
val DarkMuscleChest = Color(0xFFE53935)
val DarkMuscleBack = Color(0xFF1E88E5)
val DarkMuscleShoulders = Color(0xFF8E24AA)
val DarkMuscleBiceps = Color(0xFF00ACC1)
val DarkMuscleTriceps = Color(0xFF00897B)
val DarkMuscleCore = Color(0xFFFF9800)
val DarkMuscleGlutes = Color(0xFFD81B60)
val DarkMuscleLegs = Color(0xFF43A047)
val DarkMuscleFullBody = Color(0xFF4FC3F7)
val DarkMuscleCardio = Color(0xFFE91E63)

// Dark - Confetti colors for workout summary
val DarkConfettiColors = listOf(
    Color(0xFF4FC3F7), Color(0xFFFFD700), Color(0xFFE53935),
    Color(0xFF4CAF50), Color(0xFFFF9800), Color(0xFFE91E63),
    Color(0xFF9C27B0), Color(0xFF00BCD4)
)

// ============================================================
// LIGHT THEME - Gym Light (Clean white with energetic accents)
// ============================================================
val LightPrimary = Color(0xFF00897B)      // Teal - navy blue with dark green
val LightOnPrimary = Color(0xFFFFFFFF)
val LightPrimaryContainer = Color(0xFFB2DFDB)
val LightOnPrimaryContainer = Color(0xFF004D40)

val LightSecondary = Color(0xFF009688)    // Teal green
val LightOnSecondary = Color(0xFFFFFFFF)
val LightSecondaryContainer = Color(0xFFCCF5EB)
val LightOnSecondaryContainer = Color(0xFF001F1A)

val LightTertiary = Color(0xFFFFB300)     // Amber
val LightOnTertiary = Color(0xFFFFFFFF)
val LightTertiaryContainer = Color(0xFFFFF3CC)
val LightOnTertiaryContainer = Color(0xFF3D2E00)

val LightError = Color(0xFFDC3545)
val LightOnError = Color(0xFFFFFFFF)
val LightErrorContainer = Color(0xFFFFDAD6)
val LightOnErrorContainer = Color(0xFF410002)

val LightBackground = Color(0xFFFAFAFA)   // Off-white
val LightOnBackground = Color(0xFF1A1A1A)
val LightSurface = Color(0xFFFFFFFF)
val LightOnSurface = Color(0xFF1A1A1A)
val LightSurfaceVariant = Color(0xFFF0F0F0)
val LightOnSurfaceVariant = Color(0xFF4A4A4A)

val LightOutline = Color(0xFFE0E0E0)
val LightOutlineVariant = Color(0xFFD0D0D0)
val LightShadow = Color(0xFF000000)
val LightScrim = Color(0xFF000000)
val LightInverseSurface = Color(0xFF1A1A1A)
val LightInverseOnSurface = Color(0xFFFAFAFA)
val LightInversePrimary = Color(0xFF4DB6AC)

// Light - Difficulty Levels
val LightDifficultyBeginner = Color(0xFF2E7D32)   // Darker green
val LightDifficultyIntermediate = Color(0xFFF57C00) // Darker orange
val LightDifficultyAdvanced = Color(0xFFC62828)   // Darker red

// Light - Muscle Groups
val LightMuscleChest = Color(0xFFC62828)
val LightMuscleBack = Color(0xFF1565C0)
val LightMuscleShoulders = Color(0xFF6A1B9A)
val LightMuscleBiceps = Color(0xFF00838F)
val LightMuscleTriceps = Color(0xFF00695C)
val LightMuscleCore = Color(0xFFF57F17)
val LightMuscleGlutes = Color(0xFFAD1457)
val LightMuscleLegs = Color(0xFF2E7D32)
val LightMuscleFullBody = Color(0xFF0277BD)
val LightMuscleCardio = Color(0xFFC62828)

// Light - Confetti colors for workout summary
val LightConfettiColors = listOf(
    Color(0xFF29B6F6), Color(0xFFFFD600), Color(0xFFEF5350),
    Color(0xFF66BB6A), Color(0xFFFFB74D), Color(0xFFEC407A),
    Color(0xFFAB47BC), Color(0xFF26C6DA)
)

// Helper function to determine if a color is dark
fun Color.isDark(): Boolean {
    val luminance = 0.299 * red + 0.587 * green + 0.114 * blue
    return luminance < 0.5
}