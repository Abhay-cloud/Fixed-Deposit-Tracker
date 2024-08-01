package dev.abhaycloud.fdtracker.domain.repository

import android.content.Context
import androidx.fragment.app.FragmentActivity
import dev.abhaycloud.fdtracker.domain.model.biometrics.AuthenticationResult
import dev.abhaycloud.fdtracker.domain.model.biometrics.BiometricCheckResult
import kotlinx.coroutines.flow.Flow

interface BiometricAuthRepository {
    fun authenticateWithBiometrics(activity: FragmentActivity): Flow<AuthenticationResult>
    fun checkBiometricsAvailability(context: Context): Flow<BiometricCheckResult>
}