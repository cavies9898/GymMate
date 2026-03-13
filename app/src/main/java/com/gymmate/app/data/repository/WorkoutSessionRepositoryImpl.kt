package com.gymmate.app.data.repository

import com.gymmate.app.data.local.dao.RoutineDao
import com.gymmate.app.data.local.dao.WorkoutSessionDao
import com.gymmate.app.data.mapper.toDomain
import com.gymmate.app.data.mapper.toEntity
import com.gymmate.app.domain.model.WorkoutSession
import com.gymmate.app.domain.repository.WorkoutSessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WorkoutSessionRepositoryImpl @Inject constructor(
    private val sessionDao: WorkoutSessionDao,
    private val routineDao: RoutineDao
) : WorkoutSessionRepository {

    override fun getAllSessions(): Flow<List<WorkoutSession>> =
        sessionDao.getAllSessions().map { sessions ->
            sessions.mapNotNull { entity ->
                val routine = routineDao.getRoutineById(entity.routineId)?.toDomain()
                    ?: return@mapNotNull null
                entity.toDomain(routine)
            }
        }

    override suspend fun getSessionById(id: Long): WorkoutSession? {
        val entity = sessionDao.getSessionById(id) ?: return null
        val routine = routineDao.getRoutineById(entity.routineId)?.toDomain() ?: return null
        return entity.toDomain(routine)
    }

    override suspend fun insertSession(session: WorkoutSession): Long =
        sessionDao.insertSession(session.toEntity())

    override suspend fun updateSession(session: WorkoutSession) =
        sessionDao.updateSession(session.toEntity())
}