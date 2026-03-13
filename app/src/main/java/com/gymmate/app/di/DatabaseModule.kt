package com.gymmate.app.di

import android.content.Context
import androidx.room.Room
import com.gymmate.app.data.local.dao.ExerciseDao
import com.gymmate.app.data.local.dao.RoutineDao
import com.gymmate.app.data.local.dao.RoutineExerciseDao
import com.gymmate.app.data.local.dao.WorkoutSessionDao
import com.gymmate.app.data.local.database.GymMateDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): GymMateDatabase =
        Room.databaseBuilder(
            context,
            GymMateDatabase::class.java,
            "gymmate.db"
        ).build()

    @Provides
    fun provideExerciseDao(db: GymMateDatabase): ExerciseDao = db.exerciseDao()

    @Provides
    fun provideRoutineDao(db: GymMateDatabase): RoutineDao = db.routineDao()

    @Provides
    fun provideRoutineExerciseDao(db: GymMateDatabase): RoutineExerciseDao = db.routineExerciseDao()

    @Provides
    fun provideWorkoutSessionDao(db: GymMateDatabase): WorkoutSessionDao = db.workoutSessionDao()
}