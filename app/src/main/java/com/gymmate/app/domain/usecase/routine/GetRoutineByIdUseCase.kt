package com.gymmate.app.domain.usecase.routine

import com.gymmate.app.domain.model.Routine
import com.gymmate.app.domain.repository.RoutineRepository
import javax.inject.Inject

class GetRoutineByIdUseCase @Inject constructor(
    private val repository: RoutineRepository
) {
    suspend operator fun invoke(id: Long): Routine? = repository.getRoutineById(id)
}