package com.gymmate.app.domain.repository

import com.gymmate.app.domain.model.Routine
import com.gymmate.app.domain.model.WorkoutType
import kotlinx.coroutines.flow.Flow

interface RoutineRepository {
    fun getAllRoutines(): Flow<List<Routine>>
    fun getRoutinesByType(workoutType: WorkoutType): Flow<List<Routine>>
    fun getRoutineByIdFlow(id: Long): Flow<Routine?>
    suspend fun getRoutineById(id: Long): Routine?
    suspend fun insertRoutine(routine: Routine)
    suspend fun deleteRoutine(routine: Routine)
}