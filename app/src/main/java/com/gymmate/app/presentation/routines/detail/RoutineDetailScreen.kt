package com.gymmate.app.presentation.routines.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.gymmate.app.R
import com.gymmate.app.domain.model.DifficultyLevel
import com.gymmate.app.domain.model.Routine
import com.gymmate.app.domain.model.WorkoutType
import com.gymmate.app.presentation.navigation.AppDestinations
import com.gymmate.app.presentation.routines.detail.components.ExerciseRow
import com.gymmate.app.presentation.routines.detail.components.MuscleGroupChip
import com.gymmate.app.presentation.ui.cardGradientColors
import com.gymmate.app.presentation.ui.color
import com.gymmate.app.presentation.ui.gradientColors
import com.gymmate.app.presentation.ui.emoji

@Composable
fun RoutineDetailScreen(
    navController: NavController,
    viewModel: RoutineDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.isLoading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    val routine = uiState.routine ?: return

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            // Header
            item { RoutineDetailHeader(routine, navController) }

            // Info cards
            item {
                RoutineInfoSection(routine)
            }

            // Grupos musculares
            item {
                MuscleGroupsSection(routine)
            }

            // Ejercicios
            item {
                Text(
                    text = stringResource(R.string.routine_detail_exercises_section),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
                )
            }

            items(routine.exercises.sortedBy { it.order }) { routineExercise ->
                ExerciseRow(
                    routineExercise = routineExercise,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
                )
                Divider(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.08f)
                )
            }
        }

        // Botón fijo abajo
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            color = MaterialTheme.colorScheme.background,
            shadowElevation = 8.dp
        ) {
            Button(
                onClick = {
                    navController.navigate(
                        AppDestinations.ActiveWorkout.createRoute(routine.id)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp)
                    .navigationBarsPadding(),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = stringResource(R.string.routine_detail_start),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun RoutineDetailHeader(routine: Routine, navController: NavController) {
    val gradientColors = routine.workoutType.gradientColors()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .background(Brush.verticalGradient(gradientColors))
    ) {
        // Overlay oscuro en la parte inferior para transición suave
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(
                        listOf(Color.Transparent, MaterialTheme.colorScheme.background)
                    )
                )
        )

        // Botón back
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .statusBarsPadding()
                .padding(8.dp)
                .align(Alignment.TopStart)
                .clip(CircleShape)
                .background(Color.Black.copy(alpha = 0.3f))
        ) {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = stringResource(R.string.cd_back_button),
                tint = Color.White
            )
        }

        // Contenido central
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = routine.workoutType.emoji(),
                fontSize = 72.sp
            )
        }

        // Nombre y descripción abajo
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Text(
                text = routine.name,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = routine.description,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
private fun RoutineInfoSection(routine: Routine) {
    val difficultyColor = routine.difficulty.color()
    val difficultyLabel = when (routine.difficulty) {
        DifficultyLevel.BEGINNER -> stringResource(R.string.difficulty_beginner)
        DifficultyLevel.INTERMEDIATE -> stringResource(R.string.difficulty_intermediate)
        DifficultyLevel.ADVANCED -> stringResource(R.string.difficulty_advanced)
    }
    val difficultyDots = when (routine.difficulty) {
        DifficultyLevel.BEGINNER -> 1
        DifficultyLevel.INTERMEDIATE -> 2
        DifficultyLevel.ADVANCED -> 3
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Duración
        InfoChip(
            modifier = Modifier.weight(1f),
            icon = { Icon(Icons.Default.AccessTime, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp)) },
            label = stringResource(R.string.routine_detail_duration_label),
            value = stringResource(R.string.routine_detail_minutes_format, routine.durationMinutes)
        )

        // Ejercicios
        InfoChip(
            modifier = Modifier.weight(1f),
            icon = { Icon(Icons.Default.FitnessCenter, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp)) },
            label = stringResource(R.string.routine_detail_exercises_label),
            value = "${routine.exercises.size}"
        )

        // Dificultad
        InfoChip(
            modifier = Modifier.weight(1f),
            icon = {
                Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                    repeat(3) { index ->
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(
                                    if (index < difficultyDots) difficultyColor
                                    else difficultyColor.copy(alpha = 0.2f)
                                )
                        )
                    }
                }
            },
            label = stringResource(R.string.routine_detail_difficulty_label),
            value = difficultyLabel,
            valueColor = difficultyColor
        )
    }
}

@Composable
private fun InfoChip(
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
    label: String,
    value: String,
    valueColor: Color = Color.White
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        icon()
        Text(
            text = value,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = valueColor
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}

@Composable
private fun MuscleGroupsSection(routine: Routine) {
    val muscleGroups = routine.exercises
        .map { it.exercise.muscleGroup }
        .distinct()

    if (muscleGroups.isEmpty()) return

    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Text(
            text = stringResource(R.string.routine_detail_muscles_worked),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(8.dp))
        androidx.compose.foundation.layout.FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            muscleGroups.forEach { muscleGroup ->
                MuscleGroupChip(muscleGroup = muscleGroup)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}