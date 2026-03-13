package com.gymmate.app.domain.usecase.workout

import com.gymmate.app.domain.model.WorkoutSession
import com.gymmate.app.domain.repository.WorkoutSessionRepository
import javax.inject.Inject

class SaveWorkoutSessionUseCase @Inject constructor(
    private val repository: WorkoutSessionRepository
) {
    suspend operator fun invoke(session: WorkoutSession): Long =
        repository.insertSession(session)
}