package com.gymmate.app.domain.usecase.routine

import com.gymmate.app.domain.model.Routine
import com.gymmate.app.domain.model.WorkoutType
import com.gymmate.app.domain.repository.RoutineRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRoutinesByTypeUseCase @Inject constructor(
    private val repository: RoutineRepository
) {
    operator fun invoke(workoutType: WorkoutType): Flow<List<Routine>> =
        repository.getRoutinesByType(workoutType)
}