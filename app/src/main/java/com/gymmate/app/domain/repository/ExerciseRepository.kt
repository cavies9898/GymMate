package com.gymmate.app.domain.repository

import com.gymmate.app.domain.model.Exercise
import com.gymmate.app.domain.model.MuscleGroup
import com.gymmate.app.domain.model.WorkoutType
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {
    fun getAllExercises(): Flow<List<Exercise>>
    fun getExercisesByMuscleGroup(muscleGroup: MuscleGroup): Flow<List<Exercise>>
    fun getExercisesByWorkoutType(workoutType: WorkoutType): Flow<List<Exercise>>
    fun getExerciseByIdFlow(id: Long): Flow<Exercise?>
    suspend fun getExerciseById(id: Long): Exercise?
}