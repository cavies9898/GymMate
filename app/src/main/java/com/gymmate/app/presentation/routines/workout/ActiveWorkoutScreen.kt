package com.gymmate.app.presentation.routines.workout

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.gymmate.app.R
import com.gymmate.app.presentation.navigation.AppDestinations
import com.gymmate.app.presentation.ui.icon

@Composable
fun ActiveWorkoutScreen(
    navController: NavController,
    viewModel: ActiveWorkoutViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showExitDialog by remember { mutableStateOf(false) }

    // Navegar al summary cuando termina
    LaunchedEffect(uiState.phase) {
        if (uiState.phase == WorkoutPhase.FINISHED) {
            navController.navigate(
                AppDestinations.WorkoutSummary.createRoute(
                    routineName = uiState.routineName,
                    durationSeconds = uiState.elapsedSeconds,
                    setsCompleted = uiState.totalSetsCompleted,
                    exercisesCompleted = uiState.totalExercises
                )
            ) {
                popUpTo(AppDestinations.ActiveWorkout.route) { inclusive = true }
            }
        }
    }

    BackHandler { showExitDialog = true }

    if (uiState.isLoading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            title = { Text(stringResource(R.string.workout_exit_dialog_title)) },
            text = { Text(stringResource(R.string.workout_exit_dialog_message)) },
            confirmButton = {
                TextButton(onClick = { navController.popBackStack() }) {
                    Text(stringResource(R.string.workout_exit_confirm), color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showExitDialog = false }) {
                    Text(stringResource(R.string.workout_exit_dismiss))
                }
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            // --- Top bar ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { showExitDialog = true }) {
                    Icon(Icons.Default.Close, contentDescription = stringResource(R.string.workout_close_content_desc), tint = MaterialTheme.colorScheme.onBackground)
                }
                Text(
                    text = uiState.routineName,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold
                )
                // Timer elapsed
                Text(
                    text = formatElapsed(uiState.elapsedSeconds),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                )
            }

            // --- Progress bar ---
            val animatedProgress by animateFloatAsState(
                targetValue = uiState.progressFraction,
                animationSpec = tween(500),
                label = "progress"
            )
            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f),
                strokeCap = StrokeCap.Round
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(R.string.workout_exercise_of_total, uiState.currentExerciseIndex + 1, uiState.totalExercises),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.weight(0.5f))

            // --- Contenido principal (ejercicio o descanso) ---
            AnimatedContent(
                targetState = uiState.phase,
                transitionSpec = {
                    (slideInVertically { it } + fadeIn()) togetherWith
                            (slideOutVertically { -it } + fadeOut())
                },
                label = "phase"
            ) { phase ->
                when (phase) {
                    WorkoutPhase.EXERCISE -> ExerciseContent(uiState)
                    WorkoutPhase.REST -> RestContent(
                        secondsRemaining = uiState.restSecondsRemaining,
                        onSkip = { viewModel.skipRest() }
                    )
                    WorkoutPhase.FINISHED -> Box(Modifier.fillMaxWidth())
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // --- Botón siguiente ---
            if (uiState.phase == WorkoutPhase.EXERCISE) {
                Button(
                    onClick = { viewModel.onNextPressed() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = when {
                            uiState.isLastSet && uiState.isLastExercise -> stringResource(R.string.workout_finish_routine)
                            uiState.isLastSet -> stringResource(R.string.workout_next_exercise)
                            else -> stringResource(R.string.workout_set_completed)
                        },
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ExerciseContent(uiState: ActiveWorkoutUiState) {
    val exercise = uiState.currentExercise ?: return

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Icono grande
        Text(
            text = exercise.exercise.muscleGroup.icon(),
            fontSize = 96.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Nombre del ejercicio
        AnimatedContent(
            targetState = exercise.exercise.name,
            label = "exerciseName"
        ) { name ->
            Text(
                text = name,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = exercise.exercise.description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Sets / Reps / Duración
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Serie actual
            WorkoutStatBox(
                label = stringResource(R.string.workout_set_label),
                value = "${uiState.currentSet} / ${exercise.sets}"
            )

            // Reps o duración
            if (exercise.reps != null) {
                WorkoutStatBox(label = stringResource(R.string.workout_reps_label), value = "${exercise.reps}")
            } else if (exercise.durationSeconds != null) {
                WorkoutStatBox(label = stringResource(R.string.workout_duration_label), value = "${exercise.durationSeconds}s")
            }
        }
    }
}

@Composable
private fun RestContent(
    secondsRemaining: Int,
    onSkip: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.workout_rest_emoji), fontSize = 64.sp)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.workout_rest_title),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Timer circular
        Box(contentAlignment = Alignment.Center) {
            androidx.compose.material3.CircularProgressIndicator(
                progress = { secondsRemaining / 60f },
                modifier = Modifier.size(120.dp),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f),
                strokeWidth = 8.dp,
                strokeCap = StrokeCap.Round
            )
            Text(
                text = "$secondsRemaining",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedButton(
            onClick = onSkip,
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = stringResource(R.string.workout_rest_skip),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun WorkoutStatBox(label: String, value: String) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
        )
    }
}

private fun formatElapsed(seconds: Long): String {
    val m = seconds / 60
    val s = seconds % 60
    return "%02d:%02d".format(m, s)
}