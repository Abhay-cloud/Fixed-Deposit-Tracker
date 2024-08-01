package dev.abhaycloud.fdtracker.domain.usecase.biometrics

import android.content.Context
import dev.abhaycloud.fdtracker.domain.model.biometrics.BiometricCheckResult
import dev.abhaycloud.fdtracker.domain.repository.BiometricAuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BiometricAvailabilityUseCase @Inject constructor (private val context: Context,private val repository: BiometricAuthRepository) {
    fun execute (): Flow<BiometricCheckResult> {
        return repository.checkBiometricsAvailability(context)
    }
}