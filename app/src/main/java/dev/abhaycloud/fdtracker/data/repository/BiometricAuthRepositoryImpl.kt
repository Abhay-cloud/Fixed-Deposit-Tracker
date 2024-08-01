package dev.abhaycloud.fdtracker.data.repository

import android.content.Context
import androidx.fragment.app.FragmentActivity
import dev.abhaycloud.fdtracker.data.biometrics.BiometricAuthenticator
import dev.abhaycloud.fdtracker.data.biometrics.BiometricChecker
import dev.abhaycloud.fdtracker.domain.model.biometrics.AuthenticationResult
import dev.abhaycloud.fdtracker.domain.model.biometrics.BiometricCheckResult
import dev.abhaycloud.fdtracker.domain.repository.BiometricAuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BiometricAuthRepositoryImpl @Inject constructor (
    private val biometricChecker: BiometricChecker,
    private val biometricAuthenticator: BiometricAuthenticator
) : BiometricAuthRepository {

    override fun authenticateWithBiometrics(activity: FragmentActivity): Flow<AuthenticationResult> {
        return biometricAuthenticator.authenticate(activity)
    }

    override fun checkBiometricsAvailability(context: Context): Flow<BiometricCheckResult> = flow {
        emit(biometricChecker.checkAvailability(context))
    }
}