package com.gymmate.app.presentation.exercises.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gymmate.app.domain.model.Exercise
import com.gymmate.app.domain.usecase.exercise.GetAllExercisesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ExerciseDetailUiState(
    val isLoading: Boolean = true,
    val exercise: Exercise? = null
)

@HiltViewModel
class ExerciseDetailViewModel @Inject constructor(
    private val getAllExercisesUseCase: GetAllExercisesUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExerciseDetailUiState())
    val uiState: StateFlow<ExerciseDetailUiState> = _uiState.asStateFlow()

    init {
        val exerciseId = savedStateHandle.get<Long>("exerciseId") ?: 0L
        loadExercise(exerciseId)
    }

    private fun loadExercise(exerciseId: Long) {
        viewModelScope.launch {
            val exercise = getAllExercisesUseCase().first()
                .find { it.id == exerciseId }
            _uiState.value = ExerciseDetailUiState(
                isLoading = false,
                exercise = exercise
            )
        }
    }
}