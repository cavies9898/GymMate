package com.gymmate.app

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.gymmate.app.data.local.database.DatabaseSeeder
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class GymMateApplication : Application() {

    @Inject lateinit var seeder: DatabaseSeeder
    @Inject lateinit var dataStore: DataStore<Preferences>

    companion object {
        val KEY_DB_SEEDED = booleanPreferencesKey("db_seeded")
    }

    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Dispatchers.IO).launch {
            val prefs = dataStore.data.first()
            if (prefs[KEY_DB_SEEDED] != true) {
                seeder.seed()
                dataStore.edit { it[KEY_DB_SEEDED] = true }
            }
        }
    }
}