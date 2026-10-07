package com.gymmate.app.presentation.routines.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gymmate.app.R
import com.gymmate.app.domain.model.MuscleGroup
import com.gymmate.app.presentation.ui.color
import com.gymmate.app.presentation.ui.icon

@Composable
fun MuscleGroupChip(muscleGroup: MuscleGroup) {
    val label = when (muscleGroup) {
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
    }
    val color = muscleGroup.color()
    val emoji = muscleGroup.icon()

    Text(
        text = "${emoji}  ${label}",
        style = MaterialTheme.typography.labelMedium,
        fontWeight = FontWeight.Medium,
        color = color,
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(color.copy(alpha = 0.15f))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    )
}