package com.gymmate.app.domain.usecase.theme

import com.gymmate.app.domain.model.ThemeMode
import com.gymmate.app.domain.repository.ThemePreferenceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetThemeModeUseCase @Inject constructor(
    private val repository: ThemePreferenceRepository
) {
    operator fun invoke(): Flow<ThemeMode> = repository.getThemeMode()
}

class SetThemeModeUseCase @Inject constructor(
    private val repository: ThemePreferenceRepository
) {
    suspend operator fun invoke(themeMode: ThemeMode) = repository.setThemeMode(themeMode)
}