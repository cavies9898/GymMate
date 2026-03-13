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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gymmate.app.domain.model.WorkoutType

@Composable
fun WorkoutTypeToggle(
    selectedType: WorkoutType,
    onToggle: () -> Unit
) {
    val isHome = selectedType == WorkoutType.HOME

    val backgroundColor by animateColorAsState(
        targetValue = if (isHome) Color(0xFF0D4F6B) else Color(0xFF2D1B4E),
        animationSpec = tween(300),
        label = "toggleBg"
    )

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
                text = if (isHome) "🏠" else "🏋️",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = if (isHome) "Casa" else "Gimnasio",
                style = MaterialTheme.typography.labelLarge,
                color = Color.White
            )
        }
    }
}