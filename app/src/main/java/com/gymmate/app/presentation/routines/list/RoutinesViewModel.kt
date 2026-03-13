package com.gymmate.app.presentation.routines.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gymmate.app.domain.model.DifficultyLevel
import com.gymmate.app.domain.model.WorkoutType
import com.gymmate.app.domain.usecase.routine.GetAllRoutinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoutinesViewModel @Inject constructor(
    private val getAllRoutinesUseCase: GetAllRoutinesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RoutinesUiState())
    val uiState: StateFlow<RoutinesUiState> = _uiState.asStateFlow()

    init {
        loadRoutines()
    }

    private fun loadRoutines() {
        viewModelScope.launch {
            getAllRoutinesUseCase().collect { routines ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    allRoutines = routines
                )
                applyFilters()
            }
        }
    }

    fun toggleWorkoutType() {
        val current = _uiState.value.selectedWorkoutType
        _uiState.value = _uiState.value.copy(
            selectedWorkoutType = if (current == WorkoutType.HOME) WorkoutType.GYM else WorkoutType.HOME,
            selectedDifficulty = null
        )
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

    fun setSortOrder(sortOrder: SortOrder) {
        _uiState.value = _uiState.value.copy(sortOrder = sortOrder)
        applyFilters()
    }

    private fun applyFilters() {
        val state = _uiState.value
        var result = state.allRoutines
            .filter { it.workoutType == state.selectedWorkoutType }

        if (state.selectedDifficulty != null) {
            result = result.filter { it.difficulty == state.selectedDifficulty }
        }

        if (state.searchQuery.isNotBlank()) {
            result = result.filter {
                it.name.contains(state.searchQuery, ignoreCase = true) ||
                        it.description.contains(state.searchQuery, ignoreCase = true)
            }
        }

        result = when (state.sortOrder) {
            SortOrder.DURATION_ASC -> result.sortedBy { it.durationMinutes }
            SortOrder.DURATION_DESC -> result.sortedByDescending { it.durationMinutes }
            SortOrder.DEFAULT -> result
        }

        _uiState.value = _uiState.value.copy(filteredRoutines = result)
    }
}