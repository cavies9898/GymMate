package com.gymmate.app.presentation.exercises.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gymmate.app.domain.model.DifficultyLevel
import com.gymmate.app.domain.model.MuscleGroup
import com.gymmate.app.domain.model.WorkoutType
import com.gymmate.app.domain.usecase.exercise.GetAllExercisesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExercisesViewModel @Inject constructor(
    private val getAllExercisesUseCase: GetAllExercisesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExercisesUiState())
    val uiState: StateFlow<ExercisesUiState> = _uiState.asStateFlow()

    init {
        loadExercises()
    }

    private fun loadExercises() {
        viewModelScope.launch {
            getAllExercisesUseCase().collect { exercises ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    allExercises = exercises
                )
                applyFilters()
            }
        }
    }

    fun toggleWorkoutType() {
        val current = _uiState.value.selectedWorkoutType
        _uiState.value = _uiState.value.copy(
            selectedWorkoutType = if (current == WorkoutType.HOME) WorkoutType.GYM else WorkoutType.HOME,
            selectedMuscleGroup = null,
            selectedDifficulty = null
        )
        applyFilters()
    }

    fun selectMuscleGroup(muscleGroup: MuscleGroup?) {
        _uiState.value = _uiState.value.copy(selectedMuscleGroup = muscleGroup)
        applyFilters()
    }

    fun selectDifficulty(difficulty: DifficultyLevel?) {
        _uiState.value = _uiState.value.copy(selectedDifficulty = difficulty)
        applyFilters()
    }

    fun onSearchQueryChange(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
        applyFilters()
    }

    private fun applyFilters() {
        val state = _uiState.value
        var result = state.allExercises
            .filter { it.workoutType == state.selectedWorkoutType }

        if (state.selectedMuscleGroup != null) {
            result = result.filter { it.muscleGroup == state.selectedMuscleGroup }
        }

        if (state.selectedDifficulty != null) {
            result = result.filter { it.difficulty == state.selectedDifficulty }
        }

        if (state.searchQuery.isNotBlank()) {
            result = result.filter {
                it.name.contains(state.searchQuery, ignoreCase = true) ||
                        it.description.contains(state.searchQuery, ignoreCase = true)
            }
        }

        _uiState.value = _uiState.value.copy(filteredExercises = result)
    }
}