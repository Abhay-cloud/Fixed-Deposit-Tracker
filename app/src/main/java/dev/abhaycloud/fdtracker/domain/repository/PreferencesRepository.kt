package dev.abhaycloud.fdtracker.domain.repository

import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    val dynamicColor: Flow<Boolean>
    val darkMode: Flow<Boolean>
    val biometricAuth : Flow<Boolean>
    suspend fun setDynamicColor(enabled: Boolean)
    suspend fun setDarkMode(enabled: Boolean)
    suspend fun setBiometricAuth(enabled: Boolean)
}