package dev.abhaycloud.fdtracker.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.abhaycloud.fdtracker.data.repository.BiometricAuthRepositoryImpl
import dev.abhaycloud.fdtracker.domain.repository.BiometricAuthRepository
import dev.abhaycloud.fdtracker.domain.usecase.biometrics.BiometricAvailabilityUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BiometricsModule {

    @Binds
    @Singleton
    abstract fun bindsBiometricAuthRepository(
        biometricAuthRepoImpl: BiometricAuthRepositoryImpl
    ): BiometricAuthRepository



    companion object{
        @Provides
        fun providesBiometricAvailabilityUseCase(
            @ApplicationContext context: Context,
            repository: BiometricAuthRepository
        ):BiometricAvailabilityUseCase{
            return BiometricAvailabilityUseCase(context,repository)
        }

    }

}
