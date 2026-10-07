package com.gymmate.app.presentation.routines.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gymmate.app.domain.model.DifficultyLevel
import com.gymmate.app.domain.model.Routine
import com.gymmate.app.domain.model.WorkoutType
import com.gymmate.app.domain.usecase.routine.GetFilteredRoutinesUseCase
import com.gymmate.app.domain.usecase.routine.RoutineFilters
import com.gymmate.app.domain.usecase.routine.RoutineSortOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class RoutinesViewModel @Inject constructor(
    private val getFilteredRoutinesUseCase: GetFilteredRoutinesUseCase
) : ViewModel() {

    private val _filters = MutableStateFlow(
        RoutineFilters(
            workoutType = WorkoutType.HOME,
            difficulty = null,
            searchQuery = "",
            sortOrder = RoutineSortOrder.DEFAULT
        )
    )
    private val _uiState = MutableStateFlow(RoutinesUiState())
    val uiState: StateFlow<RoutinesUiState> = _uiState.asStateFlow()

    init {
        observeFilteredRoutines()
    }

    private fun observeFilteredRoutines() {
        viewModelScope.launch {
            _filters
                .flatMapLatest { filters ->
                    getFilteredRoutinesUseCase(filters)
                }
                .distinctUntilChanged()
                .collect { routines ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        filteredRoutines = routines,
                        selectedWorkoutType = _filters.value.workoutType,
                        selectedDifficulty = _filters.value.difficulty
                    )
                }
        }
    }

    fun toggleWorkoutType() {
        val current = _filters.value.workoutType
        val newWorkoutType = if (current == WorkoutType.HOME) WorkoutType.GYM else WorkoutType.HOME
        _filters.value = _filters.value.copy(
            workoutType = newWorkoutType,
            difficulty = null
        )
        _uiState.value = _uiState.value.copy(
            selectedWorkoutType = newWorkoutType,
            selectedDifficulty = null
        )
    }

    fun selectDifficulty(difficulty: DifficultyLevel?) {
        _filters.value = _filters.value.copy(difficulty = difficulty)
    }

    fun onSearchQueryChange(query: String) {
        _filters.value = _filters.value.copy(searchQuery = query)
    }

    fun setSortOrder(sortOrder: SortOrder) {
        val domainSortOrder = when (sortOrder) {
            SortOrder.DURATION_ASC -> RoutineSortOrder.DURATION_ASC
            SortOrder.DURATION_DESC -> RoutineSortOrder.DURATION_DESC
            else -> RoutineSortOrder.DEFAULT
        }
        _filters.value = _filters.value.copy(sortOrder = domainSortOrder)
    }
}