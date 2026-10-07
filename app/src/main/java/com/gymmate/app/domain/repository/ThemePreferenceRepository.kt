package com.gymmate.app.domain.repository

import com.gymmate.app.domain.model.ThemeMode
import kotlinx.coroutines.flow.Flow

interface ThemePreferenceRepository {
    fun getThemeMode(): Flow<ThemeMode>
    suspend fun setThemeMode(themeMode: ThemeMode)
}