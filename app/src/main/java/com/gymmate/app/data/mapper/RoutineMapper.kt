package com.gymmate.app.data.mapper

import com.gymmate.app.data.local.entity.RoutineEntity
import com.gymmate.app.data.local.entity.RoutineWithExercises
import com.gymmate.app.domain.model.DifficultyLevel
import com.gymmate.app.domain.model.Routine
import com.gymmate.app.domain.model.RoutineExercise
import com.gymmate.app.domain.model.WorkoutType

fun RoutineWithExercises.toDomain(): Routine = Routine(
    id = routine.id,
    name = routine.name,
    description = routine.description,
    workoutType = WorkoutType.valueOf(routine.workoutType),
    difficulty = DifficultyLevel.valueOf(routine.difficulty),
    durationMinutes = routine.durationMinutes,
    exercises = exercises.map { exerciseEntity ->
        RoutineExercise(
            id = exerciseEntity.id,
            exercise = exerciseEntity.toDomain(),
            sets = exerciseEntity.sets ?: 3,
            reps = exerciseEntity.reps,
            durationSeconds = exerciseEntity.durationSeconds,
            restSeconds = 60,
            order = 0
        )
    },
    imageUrl = routine.imageUrl
)

fun Routine.toEntity(): RoutineEntity = RoutineEntity(
    id = id,
    name = name,
    description = description,
    workoutType = workoutType.name,
    difficulty = difficulty.name,
    durationMinutes = durationMinutes,
    imageUrl = imageUrl
)