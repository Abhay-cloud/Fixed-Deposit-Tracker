package dev.abhaycloud.fdtracker.presentation.ui

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.abhaycloud.fdtracker.domain.model.biometrics.AuthenticationResult
import dev.abhaycloud.fdtracker.domain.model.biometrics.BiometricCheckResult
import dev.abhaycloud.fdtracker.domain.usecase.biometrics.BiometricAuthUseCase
import dev.abhaycloud.fdtracker.domain.usecase.biometrics.BiometricAvailabilityUseCase
import dev.abhaycloud.fdtracker.domain.usecase.biometrics.GetBiometricAuthUseCase
import dev.abhaycloud.fdtracker.domain.usecase.biometrics.SetBiometricAuthUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BiometricViewModel @Inject constructor(
    private val useCase: BiometricAuthUseCase,
    private val getBiometricAuthUseCase: GetBiometricAuthUseCase,
    private val setBiometricAuthUseCase: SetBiometricAuthUseCase,
    private val biometricAvailabilityUseCase: BiometricAvailabilityUseCase
) : ViewModel() {

    private val _biometricAvailability :MutableStateFlow<BiometricCheckResult>
    = MutableStateFlow(BiometricCheckResult.NoneEnrolled)
    val biometricAvailability : StateFlow<BiometricCheckResult> get() = _biometricAvailability

    private val _authResult = MutableStateFlow<AuthenticationResult?>(null)
    val authResult: StateFlow<AuthenticationResult?> get() = _authResult

    private val _biometricAuth = MutableStateFlow(false)
    val biometricAuthFlow: StateFlow<Boolean> = _biometricAuth


    init {

        getBiometricAuthUseCase.execute().map {
            _biometricAuth.value = it
        }.launchIn(viewModelScope)



        viewModelScope.launch {
            biometricAvailabilityUseCase.execute().collect {
                _biometricAvailability.value = it
            }
        }

    }


    fun setBiometricAuth(enabled: Boolean) {
        viewModelScope.launch {
            setBiometricAuthUseCase.execute(enabled)
        }
    }

    fun authenticate(activity: FragmentActivity) {
        viewModelScope.launch {
            useCase.execute(activity).collect { result ->
                _authResult.value = result
            }
        }
    }

    fun handleBiometricAuth(
        biometricAuthResult: AuthenticationResult?,
        context: Context
    ) {
        when (biometricAuthResult) {
            is AuthenticationResult.Error -> {
                if (biometricAuthResult.errorCode != BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                    Toast.makeText(context, biometricAuthResult.errorMessage, Toast.LENGTH_SHORT).show()
                }
                (context as? Activity)?.finish()
            }
            AuthenticationResult.Failure -> {
                (context as? Activity)?.finish()
            }
            AuthenticationResult.Success -> {
                return
                // Success state handled in the composable rendering below
            }
            null -> Unit
        }
    }
}