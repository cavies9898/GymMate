package com.gymmate.app.data.repository

import com.gymmate.app.data.local.dao.RoutineDao
import com.gymmate.app.data.mapper.toDomain
import com.gymmate.app.data.mapper.toEntity
import com.gymmate.app.domain.model.Routine
import com.gymmate.app.domain.model.WorkoutType
import com.gymmate.app.domain.repository.RoutineRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoutineRepositoryImpl @Inject constructor(
    private val routineDao: RoutineDao
) : RoutineRepository {

    override fun getAllRoutines(): Flow<List<Routine>> =
        routineDao.getAllRoutinesWithExercises().map { list ->
            list.map { it.toDomain() }
        }

    override fun getRoutinesByType(workoutType: WorkoutType): Flow<List<Routine>> =
        routineDao.getRoutinesByType(workoutType.name).map { list ->
            list.map { it.toDomain() }
        }

    override suspend fun getRoutineById(id: Long): Routine? =
        routineDao.getRoutineById(id)?.toDomain()

    override suspend fun insertRoutine(routine: Routine) {
        routineDao.insertRoutine(routine.toEntity())
    }

    override suspend fun deleteRoutine(routine: Routine) {
        routineDao.deleteRoutine(routine.toEntity())
    }
}