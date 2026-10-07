package com.gymmate.app.presentation.exercises.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.gymmate.app.R
import com.gymmate.app.domain.model.DifficultyLevel
import com.gymmate.app.domain.model.MuscleGroup
import com.gymmate.app.presentation.exercises.list.components.ExerciseCard
import com.gymmate.app.presentation.home.components.WorkoutTypeToggle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExercisesScreen(
    navController: NavController,
    viewModel: ExercisesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.isLoading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    Column(modifier = Modifier.fillMaxSize()) {

        // --- Header ---
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            // Título + Toggle
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.exercises_title),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                WorkoutTypeToggle(
                    selectedType = uiState.selectedWorkoutType,
                    onToggle = { viewModel.toggleWorkoutType() }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Buscador
            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = { viewModel.onSearchQueryChange(it) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(stringResource(R.string.exercises_search_hint)) },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = stringResource(R.string.exercises_search_content_desc))
                },
                trailingIcon = {
                    AnimatedVisibility(
                        visible = uiState.searchQuery.isNotEmpty(),
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        IconButton(onClick = { viewModel.onSearchQueryChange("") }) {
                            Icon(Icons.Default.Close, contentDescription = stringResource(R.string.exercises_clear_search_content_desc))
                        }
                    }
                },
                singleLine = true,
                shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.surface
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Chips grupo muscular
            Text(
                text = stringResource(R.string.exercises_muscle_label),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
            Spacer(modifier = Modifier.height(6.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                item {
                    FilterChip(
                        selected = uiState.selectedMuscleGroup == null,
                        onClick = { viewModel.selectMuscleGroup(null) },
                        label = { Text(stringResource(R.string.all)) }
                    )
                }
                items(MuscleGroup.entries) { muscle ->
                    FilterChip(
                        selected = uiState.selectedMuscleGroup == muscle,
                        onClick = { viewModel.selectMuscleGroup(muscle) },
                        label = { Text(when (muscle) {
                            MuscleGroup.CHEST -> stringResource(R.string.muscle_chest)
                            MuscleGroup.BACK -> stringResource(R.string.muscle_back)
                            MuscleGroup.SHOULDERS -> stringResource(R.string.muscle_shoulders)
                            MuscleGroup.BICEPS -> stringResource(R.string.muscle_biceps)
                            MuscleGroup.TRICEPS -> stringResource(R.string.muscle_triceps)
                            MuscleGroup.CORE -> stringResource(R.string.muscle_core)
                            MuscleGroup.GLUTES -> stringResource(R.string.muscle_glutes)
                            MuscleGroup.LEGS -> stringResource(R.string.muscle_legs)
                            MuscleGroup.FULL_BODY -> stringResource(R.string.muscle_full_body)
                            MuscleGroup.CARDIO -> stringResource(R.string.muscle_cardio)
                        }) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Chips dificultad
            Text(
                text = stringResource(R.string.exercises_difficulty_label),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
            Spacer(modifier = Modifier.height(6.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                item {
                    FilterChip(
                        selected = uiState.selectedDifficulty == null,
                        onClick = { viewModel.selectDifficulty(null) },
                        label = { Text(stringResource(R.string.all)) }
                    )
                }
                items(DifficultyLevel.entries) { difficulty ->
                    FilterChip(
                        selected = uiState.selectedDifficulty == difficulty,
                        onClick = { viewModel.selectDifficulty(difficulty) },
                        label = { Text(when (difficulty) {
                            DifficultyLevel.BEGINNER -> stringResource(R.string.difficulty_beginner)
                            DifficultyLevel.INTERMEDIATE -> stringResource(R.string.difficulty_intermediate)
                            DifficultyLevel.ADVANCED -> stringResource(R.string.difficulty_advanced)
                        }) }
                    )
                }
            }
        }

        // --- Lista ---
        if (uiState.filteredExercises.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = stringResource(R.string.exercises_empty_emoji), style = MaterialTheme.typography.displayMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(R.string.exercises_empty_state),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                    )
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(uiState.filteredExercises) { exercise ->
                    ExerciseCard(
                        exercise = exercise,
                        onClick = {
                            navController.navigate("exercises/${exercise.id}")
                        }
                    )
                }
            }
        }
    }
}