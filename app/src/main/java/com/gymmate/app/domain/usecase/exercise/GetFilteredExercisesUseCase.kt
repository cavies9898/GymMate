package com.gymmate.app.domain.usecase.exercise

import com.gymmate.app.domain.model.DifficultyLevel
import com.gymmate.app.domain.model.Exercise
import com.gymmate.app.domain.model.MuscleGroup
import com.gymmate.app.domain.model.WorkoutType
import com.gymmate.app.domain.repository.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

data class ExerciseFilters(
    val workoutType: WorkoutType = WorkoutType.HOME,
    val muscleGroup: MuscleGroup? = null,
    val difficulty: DifficultyLevel? = null,
    val searchQuery: String = ""
)

class GetFilteredExercisesUseCase @Inject constructor(
    private val repository: ExerciseRepository
) {
    operator fun invoke(filters: ExerciseFilters): Flow<List<Exercise>> =
        repository.getExercisesByWorkoutType(filters.workoutType).map { exercises ->
            var result = exercises

            filters.muscleGroup?.let {
                result = result.filter { it.muscleGroup == filters.muscleGroup }
            }

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

            result
        }
}