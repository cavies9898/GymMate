package com.gymmate.app.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gymmate.app.domain.model.WorkoutType
import com.gymmate.app.domain.usecase.home.GetHomeDataUseCase
import com.gymmate.app.domain.usecase.home.HomeData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeDataUseCase: GetHomeDataUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        observeHomeData()
    }

    private fun observeHomeData() {
        viewModelScope.launch {
            getHomeDataUseCase(_uiState.value.selectedWorkoutType)
                .distinctUntilChanged()
                .collect { data ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        userName = data.userName,
                        completedSessions = data.completedSessions,
                        activeStreak = data.activeStreak,
                        featuredRoutines = data.featuredRoutines,
                        lastSession = data.lastSession
                    )
                }
        }
    }

    fun toggleWorkoutType() {
        val current = _uiState.value.selectedWorkoutType
        _uiState.value = _uiState.value.copy(
            selectedWorkoutType = if (current == WorkoutType.HOME) WorkoutType.GYM else WorkoutType.HOME
        )
    }
}