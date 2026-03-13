package com.gymmate.app.presentation.routines.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gymmate.app.core.utils.getRoutineIcon
import com.gymmate.app.domain.model.RoutineExercise
import com.gymmate.app.domain.model.WorkoutType

@Composable
fun ExerciseRow(
    routineExercise: RoutineExercise,
    modifier: Modifier = Modifier
) {
    val exercise = routineExercise.exercise

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Número de orden
        Text(
            text = "${routineExercise.order}",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(24.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        // Icono del ejercicio
        Text(
            text = getExerciseIcon(exercise.muscleGroup.name),
            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.width(12.dp))

        // Info del ejercicio
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = exercise.name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = buildExerciseDetail(routineExercise),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
        }

        // Sets badge
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "${routineExercise.sets}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "series",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
            )
        }
    }
}

private fun buildExerciseDetail(routineExercise: RoutineExercise): String {
    return when {
        routineExercise.reps != null -> "${routineExercise.reps} reps · ${routineExercise.restSeconds}s descanso"
        routineExercise.durationSeconds != null -> "${routineExercise.durationSeconds}s · ${routineExercise.restSeconds}s descanso"
        else -> "${routineExercise.restSeconds}s descanso"
    }
}

fun getExerciseIcon(muscleGroup: String): String {
    return when (muscleGroup.uppercase()) {
        "CHEST" -> "💪"
        "BACK" -> "🏋️"
        "SHOULDERS" -> "🔱"
        "BICEPS" -> "💪"
        "TRICEPS" -> "💪"
        "CORE" -> "🎯"
        "GLUTES" -> "🦵"
        "LEGS" -> "🦵"
        "FULL_BODY" -> "⚡"
        "CARDIO" -> "🫀"
        else -> "🏃"
    }
}