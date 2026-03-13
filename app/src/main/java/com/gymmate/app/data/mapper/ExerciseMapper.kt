package com.gymmate.app.data.mapper

import com.gymmate.app.data.local.entity.ExerciseEntity
import com.gymmate.app.domain.model.DifficultyLevel
import com.gymmate.app.domain.model.Exercise
import com.gymmate.app.domain.model.MuscleGroup
import com.gymmate.app.domain.model.WorkoutType

fun ExerciseEntity.toDomain(): Exercise = Exercise(
    id = id,
    name = name,
    description = description,
    muscleGroup = MuscleGroup.valueOf(muscleGroup),
    workoutType = WorkoutType.valueOf(workoutType),
    difficulty = DifficultyLevel.valueOf(difficulty),
    durationSeconds = durationSeconds,
    reps = reps,
    sets = sets,
    imageUrl = imageUrl,
    animationAsset = animationAsset
)

fun Exercise.toEntity(): ExerciseEntity = ExerciseEntity(
    id = id,
    name = name,
    description = description,
    muscleGroup = muscleGroup.name,
    workoutType = workoutType.name,
    difficulty = difficulty.name,
    durationSeconds = durationSeconds,
    reps = reps,
    sets = sets,
    imageUrl = imageUrl,
    animationAsset = animationAsset
)