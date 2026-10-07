package com.gymmate.app.presentation.home.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gymmate.app.R
import com.gymmate.app.domain.model.WorkoutType

@Composable
fun WorkoutTypeToggle(
    selectedType: WorkoutType,
    onToggle: () -> Unit
) {
    val isHome = selectedType == WorkoutType.HOME

    val backgroundColor by animateColorAsState(
        targetValue = if (isHome) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
        animationSpec = tween(300),
        label = "toggleBg"
    )

    val emoji = if (isHome) "🏠" else "🏋️"
    val label = if (isHome) stringResource(R.string.workout_type_home) else stringResource(R.string.workout_type_gym)

    Surface(
        onClick = onToggle,
        shape = RoundedCornerShape(20.dp),
        color = backgroundColor
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = emoji,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}