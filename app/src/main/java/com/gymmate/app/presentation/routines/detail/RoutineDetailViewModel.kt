package com.gymmate.app.presentation.routines.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gymmate.app.domain.model.Routine
import com.gymmate.app.domain.usecase.routine.GetRoutineDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoutineDetailViewModel @Inject constructor(
    private val getRoutineDetailUseCase: GetRoutineDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(RoutineDetailUiState())
    val uiState: StateFlow<RoutineDetailUiState> = _uiState.asStateFlow()

    init {
        val routineId = savedStateHandle.get<Long>("routineId") ?: 0L
        loadRoutine(routineId)
    }

    private fun loadRoutine(routineId: Long) {
        viewModelScope.launch {
            getRoutineDetailUseCase(routineId)
                .collect { routine ->
                    _uiState.value = RoutineDetailUiState(
                        isLoading = false,
                        routine = routine
                    )
                }
        }
    }
}