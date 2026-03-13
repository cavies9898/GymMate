package com.gymmate.app.presentation.routines.workout

import com.gymmate.app.domain.model.RoutineExercise

enum class WorkoutPhase {
    EXERCISE,   // ejecutando el ejercicio
    REST,       // descansando entre series
    FINISHED    // rutina completada
}

data class ActiveWorkoutUiState(
    val isLoading: Boolean = true,
    val routineName: String = "",
    val exercises: List<RoutineExercise> = emptyList(),
    val currentExerciseIndex: Int = 0,
    val currentSet: Int = 1,
    val phase: WorkoutPhase = WorkoutPhase.EXERCISE,
    val restSecondsRemaining: Int = 0,
    val totalSetsCompleted: Int = 0,
    val elapsedSeconds: Long = 0L
) {
    val currentExercise: RoutineExercise? get() = exercises.getOrNull(currentExerciseIndex)
    val totalExercises: Int get() = exercises.size
    val progressFraction: Float
        get() {
            val totalSets = exercises.sumOf { it.sets }
            return if (totalSets == 0) 0f else totalSetsCompleted.toFloat() / totalSets.toFloat()
        }
    val isLastSet: Boolean get() = currentExercise?.let { currentSet >= it.sets } ?: false
    val isLastExercise: Boolean get() = currentExerciseIndex >= exercises.size - 1
}