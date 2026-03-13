package com.gymmate.app.domain.usecase.workout

import com.gymmate.app.domain.model.WorkoutSession
import com.gymmate.app.domain.repository.WorkoutSessionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllWorkoutSessionsUseCase @Inject constructor(
    private val repository: WorkoutSessionRepository
) {
    operator fun invoke(): Flow<List<WorkoutSession>> = repository.getAllSessions()
}