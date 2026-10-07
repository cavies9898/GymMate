package com.gymmate.app.data.repository

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.gymmate.app.data.local.datastore.DataStoreDelegate
import com.gymmate.app.domain.model.ThemeMode
import com.gymmate.app.domain.repository.ThemePreferenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemePreferenceRepositoryImpl @Inject constructor(
    private val dataStore: DataStoreDelegate
) : ThemePreferenceRepository {

    companion object {
        private val KEY_THEME_MODE = intPreferencesKey("theme_mode")
    }

    override fun getThemeMode(): Flow<ThemeMode> =
        dataStore.preferencesFlow.map { prefs ->
            val ordinal = prefs[KEY_THEME_MODE] ?: ThemeMode.SYSTEM.ordinal
            ThemeMode.values().getOrNull(ordinal) ?: ThemeMode.SYSTEM
        }

    override suspend fun setThemeMode(themeMode: ThemeMode) {
        dataStore.updateData { prefs ->
            prefs[KEY_THEME_MODE] = themeMode.ordinal
        }
    }
}