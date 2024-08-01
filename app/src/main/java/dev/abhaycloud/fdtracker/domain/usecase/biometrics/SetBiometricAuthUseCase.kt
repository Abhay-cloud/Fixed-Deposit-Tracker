package dev.abhaycloud.fdtracker.domain.usecase.biometrics

import dev.abhaycloud.fdtracker.domain.repository.PreferencesRepository
import javax.inject.Inject

class SetBiometricAuthUseCase  @Inject constructor(private val repository: PreferencesRepository) {
    suspend fun execute(enabled:Boolean){
        repository.setBiometricAuth(enabled)
    }
}