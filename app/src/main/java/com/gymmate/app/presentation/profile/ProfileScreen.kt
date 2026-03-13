package com.gymmate.app.presentation.profile

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gymmate.app.domain.model.DifficultyLevel
import com.gymmate.app.domain.model.FitnessGoal

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.isLoading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    if (uiState.showEditModal) {
        EditProfileModal(
            uiState = uiState,
            onDismiss = { viewModel.closeEditModal() },
            onSave = { viewModel.saveProfile() },
            onNameChange = { viewModel.onEditName(it) },
            onWeightChange = { viewModel.onEditWeight(it) },
            onHeightChange = { viewModel.onEditHeight(it) },
            onGoalChange = { viewModel.onEditGoal(it) },
            onExperienceChange = { viewModel.onEditExperience(it) }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .navigationBarsPadding()
    ) {
        // --- Header ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFF1A2C3D), Color(0xFF0D1B2A))
                    )
                )
        ) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Avatar
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "💪", fontSize = 36.sp)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = uiState.profile?.name ?: "Atleta",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Text(
                    text = goalLabel(uiState.profile?.fitnessGoal ?: FitnessGoal.STAY_ACTIVE),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            // Botón editar
            Surface(
                onClick = { viewModel.openEditModal() },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp),
                shape = CircleShape,
                color = Color.White.copy(alpha = 0.1f)
            ) {
                Icon(
                    Icons.Default.Edit,
                    contentDescription = "Editar perfil",
                    tint = Color.White,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        // --- Stats ---
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "Estadísticas",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ProfileStatCard(
                    modifier = Modifier.weight(1f),
                    emoji = "🏋️",
                    value = "${uiState.totalSessions}",
                    label = "Sesiones"
                )
                ProfileStatCard(
                    modifier = Modifier.weight(1f),
                    emoji = "🔥",
                    value = "${uiState.activeStreak}",
                    label = "Racha (días)"
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ProfileStatCard(
                    modifier = Modifier.weight(1f),
                    emoji = "⏱️",
                    value = "${uiState.totalMinutes}",
                    label = "Minutos totales"
                )
                ProfileStatCard(
                    modifier = Modifier.weight(1f),
                    emoji = "⭐",
                    value = uiState.favoriteRoutineName ?: "-",
                    label = "Rutina favorita",
                    smallValue = true
                )
            }

            // --- Info del perfil ---
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Mi perfil",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(12.dp))

            ProfileInfoRow(
                label = "Nivel",
                value = experienceLabel(uiState.profile?.experienceLevel ?: DifficultyLevel.BEGINNER)
            )
            ProfileInfoRow(
                label = "Peso",
                value = uiState.profile?.weight?.let { "${it} kg" } ?: "No definido"
            )
            ProfileInfoRow(
                label = "Estatura",
                value = uiState.profile?.height?.let { "${it} cm" } ?: "No definido"
            )
            ProfileInfoRow(
                label = "Objetivo",
                value = goalLabel(uiState.profile?.fitnessGoal ?: FitnessGoal.STAY_ACTIVE)
            )
        }
    }
}

@Composable
private fun ProfileStatCard(
    modifier: Modifier = Modifier,
    emoji: String,
    value: String,
    label: String,
    smallValue: Boolean = false
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(text = emoji, fontSize = 24.sp)
        Text(
            text = value,
            style = if (smallValue) MaterialTheme.typography.bodyMedium
            else MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            maxLines = 2
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun ProfileInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.06f))
    )
}

fun goalLabel(goal: FitnessGoal) = when (goal) {
    FitnessGoal.LOSE_WEIGHT -> "Perder peso"
    FitnessGoal.BUILD_MUSCLE -> "Ganar músculo"
    FitnessGoal.IMPROVE_ENDURANCE -> "Mejorar resistencia"
    FitnessGoal.STAY_ACTIVE -> "Mantenerme activo"
}

fun experienceLabel(level: DifficultyLevel) = when (level) {
    DifficultyLevel.BEGINNER -> "Principiante"
    DifficultyLevel.INTERMEDIATE -> "Intermedio"
    DifficultyLevel.ADVANCED -> "Avanzado"
}