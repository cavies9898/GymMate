package com.gymmate.app.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.gymmate.app.domain.model.DifficultyLevel
import com.gymmate.app.domain.model.FitnessGoal
import com.gymmate.app.domain.model.UserProfile
import com.gymmate.app.domain.model.WorkoutType
import com.gymmate.app.domain.repository.UserProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserProfileRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : UserProfileRepository {

    companion object {
        val KEY_NAME = stringPreferencesKey("user_name")
        val KEY_GOAL = stringPreferencesKey("fitness_goal")
        val KEY_WORKOUT_TYPE = stringPreferencesKey("preferred_workout_type")
        val KEY_WEEKLY_GOAL = intPreferencesKey("weekly_goal_days")
        val KEY_WEIGHT = floatPreferencesKey("weight")
        val KEY_HEIGHT = floatPreferencesKey("height")
        val KEY_EXPERIENCE = stringPreferencesKey("experience_level")
    }

    override fun getUserProfile(): Flow<UserProfile?> =
        dataStore.data.map { prefs ->
            val name = prefs[KEY_NAME] ?: return@map null
            UserProfile(
                name = name,
                fitnessGoal = FitnessGoal.valueOf(
                    prefs[KEY_GOAL] ?: FitnessGoal.STAY_ACTIVE.name
                ),
                preferredWorkoutType = WorkoutType.valueOf(
                    prefs[KEY_WORKOUT_TYPE] ?: WorkoutType.HOME.name
                ),
                weeklyGoalDays = prefs[KEY_WEEKLY_GOAL] ?: 3,
                weight = prefs[KEY_WEIGHT],
                height = prefs[KEY_HEIGHT],
                experienceLevel = DifficultyLevel.valueOf(
                    prefs[KEY_EXPERIENCE] ?: DifficultyLevel.BEGINNER.name
                )
            )
        }

    override suspend fun saveUserProfile(profile: UserProfile) {
        dataStore.edit { prefs ->
            prefs[KEY_NAME] = profile.name
            prefs[KEY_GOAL] = profile.fitnessGoal.name
            prefs[KEY_WORKOUT_TYPE] = profile.preferredWorkoutType.name
            prefs[KEY_WEEKLY_GOAL] = profile.weeklyGoalDays
            profile.weight?.let { prefs[KEY_WEIGHT] = it }
            profile.height?.let { prefs[KEY_HEIGHT] = it }
            prefs[KEY_EXPERIENCE] = profile.experienceLevel.name
        }
    }
}