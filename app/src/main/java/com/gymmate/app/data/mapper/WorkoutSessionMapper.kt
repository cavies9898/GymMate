package com.gymmate.app.data.mapper

import com.gymmate.app.data.local.entity.WorkoutSessionEntity
import com.gymmate.app.domain.model.Routine
import com.gymmate.app.domain.model.WorkoutSession
import java.time.LocalDateTime

fun WorkoutSessionEntity.toDomain(routine: Routine): WorkoutSession = WorkoutSession(
    id = id,
    routine = routine,
    startedAt = LocalDateTime.parse(startedAt),
    completedAt = completedAt?.let { LocalDateTime.parse(it) },
    durationMinutes = durationMinutes,
    completed = completed
)

fun WorkoutSession.toEntity(): WorkoutSessionEntity = WorkoutSessionEntity(
    id = id,
    routineId = routine.id,
    startedAt = startedAt.toString(),
    completedAt = completedAt?.toString(),
    durationMinutes = durationMinutes,
    completed = completed
)