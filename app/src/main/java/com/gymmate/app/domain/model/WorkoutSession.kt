package com.gymmate.app.domain.model

import java.time.LocalDateTime

data class WorkoutSession(
    val id: Long = 0,
    val routine: Routine,
    val startedAt: LocalDateTime,
    val completedAt: LocalDateTime? = null,
    val durationMinutes: Int = 0,
    val completed: Boolean = false
)