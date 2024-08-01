package dev.abhaycloud.fdtracker.data.biometrics


import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import dev.abhaycloud.fdtracker.domain.model.biometrics.AuthenticationResult
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.concurrent.Executor
import javax.inject.Inject

class BiometricAuthenticator @Inject constructor(
) {
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt

    fun authenticate(activity: FragmentActivity): Flow<AuthenticationResult> = callbackFlow {
        executor = ContextCompat.getMainExecutor(activity)

        val callback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                trySend(AuthenticationResult.Error(errorCode, errString.toString())).isSuccess
                close()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                trySend(AuthenticationResult.Success).isSuccess
                close()
            }

            override fun onAuthenticationFailed() {
                trySend(AuthenticationResult.Failure).isSuccess
            }
        }

        biometricPrompt = BiometricPrompt(activity, executor, callback)

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("FD Tracker")
            .setSubtitle("Scan your fingerprint to continue")
            /** negative button text can be used if device credentials are not used */
//            .setNegativeButtonText("Close App")
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
            .build()

        biometricPrompt.authenticate(promptInfo)
        awaitClose { biometricPrompt.cancelAuthentication() }
    }
}
