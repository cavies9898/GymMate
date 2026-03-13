package com.gymmate.app.presentation.routines.detail

import com.gymmate.app.domain.model.Routine

data class RoutineDetailUiState(
    val isLoading: Boolean = true,
    val routine: Routine? = null,
    val error: String? = null
)