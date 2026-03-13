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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.gymmate.app.core.utils.getRoutineIcon
import com.gymmate.app.domain.model.DifficultyLevel
import com.gymmate.app.domain.model.Routine
import com.gymmate.app.domain.model.WorkoutType
import com.gymmate.app.presentation.navigation.AppDestinations
import com.gymmate.app.presentation.routines.detail.components.ExerciseRow
import com.gymmate.app.presentation.routines.detail.components.MuscleGroupChip

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
                    text = "Ejercicios",
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
                    text = "⚡  Iniciar rutina",
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
    val gradientColors = if (routine.workoutType == WorkoutType.HOME) {
        listOf(Color(0xFF0D2B4E), Color(0xFF0D4F6B), Color(0xFF1A2C3D))
    } else {
        listOf(Color(0xFF1A0D2E), Color(0xFF2D1B4E), Color(0xFF1A2C3D))
    }

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
                        listOf(Color.Transparent, Color(0xFF0D1B2A))
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
                contentDescription = "Volver",
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
                text = getRoutineIcon(routine.name, routine.workoutType),
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
    val difficultyColor = when (routine.difficulty) {
        DifficultyLevel.BEGINNER -> Color(0xFF4CAF50)
        DifficultyLevel.INTERMEDIATE -> Color(0xFFFF9800)
        DifficultyLevel.ADVANCED -> Color(0xFFE53935)
    }
    val difficultyLabel = when (routine.difficulty) {
        DifficultyLevel.BEGINNER -> "Principiante"
        DifficultyLevel.INTERMEDIATE -> "Intermedio"
        DifficultyLevel.ADVANCED -> "Avanzado"
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
            label = "Duración",
            value = "${routine.durationMinutes} min"
        )

        // Ejercicios
        InfoChip(
            modifier = Modifier.weight(1f),
            icon = { Icon(Icons.Default.FitnessCenter, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp)) },
            label = "Ejercicios",
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
            label = "Nivel",
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
            text = "Músculos trabajados",
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