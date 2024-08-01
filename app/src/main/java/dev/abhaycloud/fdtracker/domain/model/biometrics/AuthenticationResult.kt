package dev.abhaycloud.fdtracker.domain.model.biometrics

sealed interface AuthenticationResult {
    data object Success : AuthenticationResult
    data object Failure : AuthenticationResult
    data class Error(val errorCode: Int, val errorMessage: String) : AuthenticationResult
}