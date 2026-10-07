package com.gymmate.app.presentation.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.gymmate.app.R
import com.gymmate.app.domain.model.DifficultyLevel
import com.gymmate.app.domain.model.FitnessGoal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileModal(
    uiState: ProfileUiState,
    onDismiss: () -> Unit,
    onSave: () -> Unit,
    onNameChange: (String) -> Unit,
    onWeightChange: (String) -> Unit,
    onHeightChange: (String) -> Unit,
    onGoalChange: (FitnessGoal) -> Unit,
    onExperienceChange: (DifficultyLevel) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = stringResource(R.string.edit_profile_title),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Nombre
            OutlinedTextField(
                value = uiState.editName,
                onValueChange = onNameChange,
                label = { Text(stringResource(R.string.edit_profile_name_hint)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Peso y estatura
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = uiState.editWeight?.toString() ?: "",
                    onValueChange = onWeightChange,
                    label = { Text(stringResource(R.string.edit_profile_weight_hint)) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
                OutlinedTextField(
                    value = uiState.editHeight?.toString() ?: "",
                    onValueChange = onHeightChange,
                    label = { Text(stringResource(R.string.edit_profile_height_hint)) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Objetivo
            Text(
                text = stringResource(R.string.edit_profile_goal_label),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FitnessGoal.entries.forEach { goal ->
                    FilterChip(
                        selected = uiState.editGoal == goal,
                        onClick = { onGoalChange(goal) },
                        label = { Text(when (goal) {
                            FitnessGoal.LOSE_WEIGHT -> stringResource(R.string.goal_lose_weight)
                            FitnessGoal.BUILD_MUSCLE -> stringResource(R.string.goal_build_muscle)
                            FitnessGoal.IMPROVE_ENDURANCE -> stringResource(R.string.goal_improve_endurance)
                            FitnessGoal.STAY_ACTIVE -> stringResource(R.string.goal_stay_active)
                        }, style = MaterialTheme.typography.labelSmall) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Nivel
            Text(
                text = stringResource(R.string.edit_profile_experience_label),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                DifficultyLevel.entries.forEach { level ->
                    FilterChip(
                        selected = uiState.editExperience == level,
                        onClick = { onExperienceChange(level) },
                        label = { Text(when (level) {
                            DifficultyLevel.BEGINNER -> stringResource(R.string.difficulty_beginner)
                            DifficultyLevel.INTERMEDIATE -> stringResource(R.string.difficulty_intermediate)
                            DifficultyLevel.ADVANCED -> stringResource(R.string.difficulty_advanced)
                        }) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botones
            Button(
                onClick = onSave,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = stringResource(R.string.save),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.cancel), color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}