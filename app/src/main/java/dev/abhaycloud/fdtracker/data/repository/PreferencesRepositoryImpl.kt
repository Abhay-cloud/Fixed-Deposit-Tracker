package dev.abhaycloud.fdtracker.data.repository

import dev.abhaycloud.fdtracker.data.preferences.PreferencesDataSource
import dev.abhaycloud.fdtracker.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PreferencesRepositoryImpl @Inject constructor(private val dataSource: PreferencesDataSource) : PreferencesRepository {

    override val darkMode: Flow<Boolean>
        get() = dataSource.darkMode

    override val dynamicColor: Flow<Boolean>
        get() = dataSource.dynamicColor

    override val biometricAuth: Flow<Boolean>
        get() = dataSource.biometricAuth

    override suspend fun setDarkMode(enabled: Boolean) {
        dataSource.setDarkMode(enabled)
    }

    override suspend fun setDynamicColor(enabled: Boolean) {
        dataSource.setDynamicColor(enabled)
    }

    override suspend fun setBiometricAuth(enabled: Boolean) {
        dataSource.setBiometricAuth(enabled)
    }
}