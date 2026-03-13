package com.gymmate.app.presentation.routines.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gymmate.app.domain.model.MuscleGroup

@Composable
fun MuscleGroupChip(muscleGroup: MuscleGroup) {
    val (label, color) = when (muscleGroup) {
        MuscleGroup.CHEST -> "Pecho" to Color(0xFFE53935)
        MuscleGroup.BACK -> "Espalda" to Color(0xFF1E88E5)
        MuscleGroup.SHOULDERS -> "Hombros" to Color(0xFF8E24AA)
        MuscleGroup.BICEPS -> "Bíceps" to Color(0xFF00ACC1)
        MuscleGroup.TRICEPS -> "Tríceps" to Color(0xFF00897B)
        MuscleGroup.CORE -> "Core" to Color(0xFFFF9800)
        MuscleGroup.GLUTES -> "Glúteos" to Color(0xFFD81B60)
        MuscleGroup.LEGS -> "Piernas" to Color(0xFF43A047)
        MuscleGroup.FULL_BODY -> "Cuerpo completo" to Color(0xFF4FC3F7)
        MuscleGroup.CARDIO -> "Cardio" to Color(0xFFE91E63)
    }

    Text(
        text = "${getExerciseIcon(muscleGroup.name)}  $label",
        style = MaterialTheme.typography.labelMedium,
        fontWeight = FontWeight.Medium,
        color = color,
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(color.copy(alpha = 0.15f))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    )
}