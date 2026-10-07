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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gymmate.app.R
import com.gymmate.app.domain.model.RoutineExercise
import com.gymmate.app.presentation.ui.icon

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
            text = exercise.muscleGroup.icon(),
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
                text = when {
                    routineExercise.reps != null -> stringResource(R.string.routine_detail_reps_format, routineExercise.reps, routineExercise.restSeconds)
                    routineExercise.durationSeconds != null -> stringResource(R.string.routine_detail_duration_format, routineExercise.durationSeconds, routineExercise.restSeconds)
                    else -> stringResource(R.string.routine_detail_rest_only_format, routineExercise.restSeconds)
                },
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
                text = stringResource(R.string.routine_detail_sets_label),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
            )
        }
    }
}