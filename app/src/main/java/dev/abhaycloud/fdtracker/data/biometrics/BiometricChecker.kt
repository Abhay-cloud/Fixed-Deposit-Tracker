package dev.abhaycloud.fdtracker.data.biometrics

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.fragment.app.FragmentActivity
import dev.abhaycloud.fdtracker.domain.model.biometrics.BiometricCheckResult
import javax.inject.Inject


class BiometricChecker @Inject constructor() {
    fun checkAvailability(context: Context): BiometricCheckResult {
        val biometricManager = BiometricManager.from(context)
        return when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> BiometricCheckResult.NoHardware
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> BiometricCheckResult.HardwareUnavailable
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> BiometricCheckResult.NoneEnrolled
            BiometricManager.BIOMETRIC_SUCCESS -> BiometricCheckResult.Available
            else -> BiometricCheckResult.HardwareUnavailable
        }
    }
}
