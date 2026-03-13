package com.gymmate.app.domain.repository

import com.gymmate.app.domain.model.WorkoutSession
import kotlinx.coroutines.flow.Flow

interface WorkoutSessionRepository {
    fun getAllSessions(): Flow<List<WorkoutSession>>
    suspend fun getSessionById(id: Long): WorkoutSession?
    suspend fun insertSession(session: WorkoutSession): Long
    suspend fun updateSession(session: WorkoutSession)
}