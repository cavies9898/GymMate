package com.gymmate.app.domain.usecase.routine

import com.gymmate.app.domain.model.DifficultyLevel
import com.gymmate.app.domain.model.Routine
import com.gymmate.app.domain.model.WorkoutType
import com.gymmate.app.domain.repository.RoutineRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

data class RoutineFilters(
    val workoutType: WorkoutType = WorkoutType.HOME,
    val difficulty: DifficultyLevel? = null,
    val searchQuery: String = "",
    val sortOrder: RoutineSortOrder = RoutineSortOrder.DEFAULT
)

enum class RoutineSortOrder {
    DEFAULT,
    DURATION_ASC,
    DURATION_DESC
}

class GetFilteredRoutinesUseCase @Inject constructor(
    private val repository: RoutineRepository
) {
    operator fun invoke(filters: RoutineFilters): Flow<List<Routine>> =
        repository.getRoutinesByType(filters.workoutType).map { routines ->
            var result = routines

            filters.difficulty?.let {
                result = result.filter { it.difficulty == filters.difficulty }
            }

            if (filters.searchQuery.isNotBlank()) {
                val query = filters.searchQuery.lowercase()
                result = result.filter {
                    it.name.lowercase().contains(query) ||
                    it.description.lowercase().contains(query)
                }
            }

            result = when (filters.sortOrder) {
                RoutineSortOrder.DURATION_ASC -> result.sortedBy { it.durationMinutes }
                RoutineSortOrder.DURATION_DESC -> result.sortedByDescending { it.durationMinutes }
                RoutineSortOrder.DEFAULT -> result
            }

            result
        }
}