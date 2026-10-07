package com.gymmate.app.presentation.home

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.gymmate.app.R
import com.gymmate.app.presentation.home.components.FeaturedRoutineCard
import com.gymmate.app.presentation.home.components.LastSessionCard
import com.gymmate.app.presentation.home.components.StatCard
import com.gymmate.app.presentation.home.components.WorkoutTypeToggle

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        // --- Header con toggle ---
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.home_greeting),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                    )
                    Text(
                        text = uiState.userName,
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                WorkoutTypeToggle(
                    selectedType = uiState.selectedWorkoutType,
                    onToggle = { viewModel.toggleWorkoutType() }
                )
            }
        }

        // --- Stats ---
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    modifier = Modifier.weight(1f),
                    label = stringResource(R.string.home_stats_sessions_label),
                    value = uiState.completedSessions.toString(),
                    unit = stringResource(R.string.home_stats_sessions_unit)
                )
                StatCard(
                    modifier = Modifier.weight(1f),
                    label = stringResource(R.string.home_stats_streak_label),
                    value = uiState.activeStreak.toString(),
                    unit = stringResource(R.string.home_stats_streak_unit)
                )
            }
        }

        // --- Featured Routines ---
        item {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(R.string.home_featured_routines),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    uiState.featuredRoutines.filter {
                        it.workoutType == uiState.selectedWorkoutType
                    }
                ) { routine ->
                    FeaturedRoutineCard(
                        routine = routine,
                        onClick = { navController.navigate("routines/${routine.id}") }
                    )
                }
            }
        }

        // --- Última sesión ---
        uiState.lastSession?.let { session ->
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = stringResource(R.string.home_continue_session),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                LastSessionCard(
                    session = session,
                    modifier = Modifier.padding(horizontal = 20.dp),
                    onClick = {
                        navController.navigate("routines/${session.routine.id}")
                    }
                )
            }
        }
    }
}