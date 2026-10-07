package com.gymmate.app.data.repository

import com.gymmate.app.data.local.dao.ExerciseDao
import com.gymmate.app.data.mapper.toDomain
import com.gymmate.app.domain.model.Exercise
import com.gymmate.app.domain.model.MuscleGroup
import com.gymmate.app.domain.model.WorkoutType
import com.gymmate.app.domain.repository.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExerciseRepositoryImpl @Inject constructor(
    private val exerciseDao: ExerciseDao
) : ExerciseRepository {

    override fun getAllExercises(): Flow<List<Exercise>> =
        exerciseDao.getAllExercises().map { list -> list.map { it.toDomain() } }

    override fun getExercisesByMuscleGroup(muscleGroup: MuscleGroup): Flow<List<Exercise>> =
        exerciseDao.getExercisesByMuscleGroup(muscleGroup.name).map { list ->
            list.map { it.toDomain() }
        }

    override fun getExercisesByWorkoutType(workoutType: WorkoutType): Flow<List<Exercise>> =
        exerciseDao.getExercisesByWorkoutType(workoutType.name).map { list ->
            list.map { it.toDomain() }
        }

    override fun getExerciseByIdFlow(id: Long): Flow<Exercise?> =
        exerciseDao.getExerciseByIdFlow(id).map { it?.toDomain() }

    override suspend fun getExerciseById(id: Long): Exercise? =
        exerciseDao.getExerciseById(id)?.toDomain()
}