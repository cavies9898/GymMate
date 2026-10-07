package com.gymmate.app.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreDelegate @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    val preferencesFlow: Flow<Preferences> = dataStore.data

    suspend fun updateData(transform: suspend (MutablePreferences) -> Unit) {
        dataStore.edit { prefs ->
            transform(prefs.toMutablePreferences())
        }
    }
}