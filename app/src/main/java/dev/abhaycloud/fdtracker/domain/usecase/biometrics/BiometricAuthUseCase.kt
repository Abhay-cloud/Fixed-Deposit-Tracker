package dev.abhaycloud.fdtracker.domain.usecase.biometrics

import androidx.fragment.app.FragmentActivity
import dev.abhaycloud.fdtracker.domain.model.biometrics.AuthenticationResult
import dev.abhaycloud.fdtracker.domain.repository.BiometricAuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class BiometricAuthUseCase @Inject constructor (private val repository: BiometricAuthRepository) {
    fun execute(context:FragmentActivity): Flow<AuthenticationResult> {
        return repository.authenticateWithBiometrics(context)
    }
}