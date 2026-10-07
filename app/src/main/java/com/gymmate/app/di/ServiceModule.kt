package com.gymmate.app.di

import com.gymmate.app.data.service.VibrationServiceImpl
import com.gymmate.app.domain.service.VibrationService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {

    @Binds
    @Singleton
    abstract fun bindVibrationService(impl: VibrationServiceImpl): VibrationService
}