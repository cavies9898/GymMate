package com.gymmate.app.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gymmate.app.R
import com.gymmate.app.domain.model.Routine
import com.gymmate.app.presentation.ui.cardGradientColors
import com.gymmate.app.presentation.ui.emoji

@Composable
fun FeaturedRoutineCard(
    routine: Routine,
    onClick: () -> Unit
) {
    val gradientColors = routine.workoutType.cardGradientColors()
    val workoutIcon = routine.workoutType.emoji()
    val workoutLabel = when (routine.workoutType) {
        com.gymmate.app.domain.model.WorkoutType.HOME -> stringResource(R.string.workout_type_home)
        com.gymmate.app.domain.model.WorkoutType.GYM -> stringResource(R.string.workout_type_gym)
    }

    Card(
        onClick = onClick,
        modifier = Modifier
            .width(200.dp)
            .height(240.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(gradientColors))
                .padding(16.dp)
        ) {
            // Badge tipo arriba derecha
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "${workoutIcon} ${workoutLabel}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            // Icono grande + descripción en el centro
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = workoutIcon,
                    fontSize = 48.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = routine.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.6f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }

            // Nombre + duración abajo
            Column(modifier = Modifier.align(Alignment.BottomStart)) {
                Text(
                    text = routine.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = androidx.compose.ui.graphics.Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(
                        R.string.routine_detail_minutes_format,
                        routine.durationMinutes
                    ) + " · " + when (routine.difficulty) {
                        com.gymmate.app.domain.model.DifficultyLevel.BEGINNER -> stringResource(R.string.difficulty_beginner)
                        com.gymmate.app.domain.model.DifficultyLevel.INTERMEDIATE -> stringResource(R.string.difficulty_intermediate)
                        com.gymmate.app.domain.model.DifficultyLevel.ADVANCED -> stringResource(R.string.difficulty_advanced)
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.7f)
                )
            }
        }
    }
}