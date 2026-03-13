package com.gymmate.app.presentation.exercises.list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gymmate.app.domain.model.DifficultyLevel
import com.gymmate.app.domain.model.Exercise
import com.gymmate.app.presentation.routines.detail.components.getExerciseIcon

@Composable
fun ExerciseCard(
    exercise: Exercise,
    onClick: () -> Unit
) {
    val difficultyColor = when (exercise.difficulty) {
        DifficultyLevel.BEGINNER -> Color(0xFF4CAF50)
        DifficultyLevel.INTERMEDIATE -> Color(0xFFFF9800)
        DifficultyLevel.ADVANCED -> Color(0xFFE53935)
    }
    val difficultyLabel = when (exercise.difficulty) {
        DifficultyLevel.BEGINNER -> "Principiante"
        DifficultyLevel.INTERMEDIATE -> "Intermedio"
        DifficultyLevel.ADVANCED -> "Avanzado"
    }
    val muscleLabel = when (exercise.muscleGroup.name) {
        "CHEST" -> "Pecho"
        "BACK" -> "Espalda"
        "SHOULDERS" -> "Hombros"
        "BICEPS" -> "Bíceps"
        "TRICEPS" -> "Tríceps"
        "CORE" -> "Core"
        "GLUTES" -> "Glúteos"
        "LEGS" -> "Piernas"
        "FULL_BODY" -> "Cuerpo completo"
        "CARDIO" -> "Cardio"
        else -> exercise.muscleGroup.name
    }

    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icono
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = getExerciseIcon(exercise.muscleGroup.name),
                    fontSize = 26.sp
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            // Info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = exercise.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = muscleLabel,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = exercise.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Badge dificultad
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(difficultyColor.copy(alpha = 0.15f))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = difficultyLabel,
                    style = MaterialTheme.typography.labelSmall,
                    color = difficultyColor,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}