package com.gymmate.app.presentation.routines.workout

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.gymmate.app.presentation.navigation.AppDestinations
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

// Colores del confetti
private val confettiColors = listOf(
    Color(0xFF4FC3F7), Color(0xFFFFD700), Color(0xFFE53935),
    Color(0xFF4CAF50), Color(0xFFFF9800), Color(0xFFE91E63),
    Color(0xFF9C27B0), Color(0xFF00BCD4)
)

private data class ConfettiParticle(
    val x: Float,
    val y: Float,
    val color: Color,
    val radius: Float,
    val angle: Float,
    val speed: Float,
    val rotation: Float
)

@Composable
fun WorkoutSummaryScreen(
    navController: NavController,
    routineName: String,
    durationSeconds: Long,
    setsCompleted: Int,
    exercisesCompleted: Int
) {
    val estimatedCalories = ((durationSeconds / 60f) * 6).toInt()

    // Animación de entrada
    val alpha = remember { Animatable(0f) }
    val scale = remember { Animatable(0.8f) }

    // Confetti
    val particles = remember {
        List(80) {
            ConfettiParticle(
                x = Random.nextFloat(),
                y = Random.nextFloat() * -0.5f,
                color = confettiColors.random(),
                radius = Random.nextFloat() * 8f + 4f,
                angle = Random.nextFloat() * 360f,
                speed = Random.nextFloat() * 0.003f + 0.001f,
                rotation = Random.nextFloat() * 360f
            )
        }
    }

    val confettiAlpha = remember { Animatable(1f) }
    var confettiProgress by remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        alpha.animateTo(1f, animationSpec = tween(600))
        scale.animateTo(1f, animationSpec = tween(600))
        // confetti cae
        var elapsed = 0f
        while (elapsed < 1f) {
            delay(16)
            elapsed += 0.005f
            confettiProgress = elapsed
        }
        // fade out suave al terminar
        confettiAlpha.animateTo(
            targetValue = 0f,
            animationSpec = tween(durationMillis = 800)
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0D1B2A))
    ) {
        // Confetti canvas
        Canvas(modifier = Modifier.fillMaxSize()) {
            particles.forEach { particle ->
                val x = particle.x * size.width
                val y = (particle.y + confettiProgress * particle.speed * 300) * size.height
                if (y < size.height * 1.2f) {
                    drawCircle(
                        color = particle.color,
                        radius = particle.radius,
                        center = Offset(x, y)
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(0.5f))

            // Trofeo
            Text(text = "🏆", fontSize = 80.sp)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "¡Rutina completada!",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = routineName,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.weight(0.5f))

            // Stats grid
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SummaryStatCard(
                    modifier = Modifier.weight(1f),
                    emoji = "⏱️",
                    value = formatDuration(durationSeconds),
                    label = "Tiempo"
                )
                SummaryStatCard(
                    modifier = Modifier.weight(1f),
                    emoji = "🔥",
                    value = "$estimatedCalories",
                    label = "Calorías"
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SummaryStatCard(
                    modifier = Modifier.weight(1f),
                    emoji = "💪",
                    value = "$setsCompleted",
                    label = "Series"
                )
                SummaryStatCard(
                    modifier = Modifier.weight(1f),
                    emoji = "✅",
                    value = "$exercisesCompleted",
                    label = "Ejercicios"
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Botones
            Button(
                onClick = {
                    navController.navigate(AppDestinations.Home.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = "🏠  Volver al inicio",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = {
                    navController.navigate(AppDestinations.Routines.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(
                    text = "🏋️  Ver otras rutinas",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun SummaryStatCard(
    modifier: Modifier = Modifier,
    emoji: String,
    value: String,
    label: String
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White.copy(alpha = 0.07f))
            .padding(vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(text = emoji, fontSize = 28.sp)
        Text(
            text = value,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = Color.White.copy(alpha = 0.5f)
        )
    }
}

private fun formatDuration(seconds: Long): String {
    val m = seconds / 60
    val s = seconds % 60
    return if (m > 0) "${m}m ${s}s" else "${s}s"
}