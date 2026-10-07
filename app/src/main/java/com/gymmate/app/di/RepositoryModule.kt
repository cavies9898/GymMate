package com.gymmate.app.di

import com.gymmate.app.data.repository.ExerciseRepositoryImpl
import com.gymmate.app.data.repository.RoutineRepositoryImpl
import com.gymmate.app.data.repository.ThemePreferenceRepositoryImpl
import com.gymmate.app.data.repository.UserProfileRepositoryImpl
import com.gymmate.app.data.repository.WorkoutSessionRepositoryImpl
import com.gymmate.app.domain.repository.ExerciseRepository
import com.gymmate.app.domain.repository.RoutineRepository
import com.gymmate.app.domain.repository.ThemePreferenceRepository
import com.gymmate.app.domain.repository.UserProfileRepository
import com.gymmate.app.domain.repository.WorkoutSessionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRoutineRepository(impl: RoutineRepositoryImpl): RoutineRepository

    @Binds
    @Singleton
    abstract fun bindExerciseRepository(impl: ExerciseRepositoryImpl): ExerciseRepository

    @Binds
    @Singleton
    abstract fun bindWorkoutSessionRepository(impl: WorkoutSessionRepositoryImpl): WorkoutSessionRepository

    @Binds
    @Singleton
    abstract fun bindUserProfileRepository(impl: UserProfileRepositoryImpl): UserProfileRepository

    @Binds
    @Singleton
    abstract fun bindThemePreferenceRepository(impl: ThemePreferenceRepositoryImpl): ThemePreferenceRepository
}