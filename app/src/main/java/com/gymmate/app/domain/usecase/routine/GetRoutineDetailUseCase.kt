package com.gymmate.app.domain.usecase.routine

import com.gymmate.app.domain.model.Routine
import com.gymmate.app.domain.repository.RoutineRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRoutineDetailUseCase @Inject constructor(
    private val repository: RoutineRepository
) {
    operator fun invoke(routineId: Long): Flow<Routine?> =
        repository.getRoutineByIdFlow(routineId)
}