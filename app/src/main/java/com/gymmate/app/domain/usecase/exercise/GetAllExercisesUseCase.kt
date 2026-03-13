package com.gymmate.app.domain.usecase.exercise

import com.gymmate.app.domain.model.Exercise
import com.gymmate.app.domain.repository.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllExercisesUseCase @Inject constructor(
    private val repository: ExerciseRepository
) {
    operator fun invoke(): Flow<List<Exercise>> = repository.getAllExercises()
}