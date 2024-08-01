package dev.abhaycloud.fdtracker.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("settings")

class PreferencesDataSource(private val context: Context) {
    val dynamicColor: Flow<Boolean> = context.dataStore.data.map {
        it[PreferencesKeys.DYNAMIC_COLOR] ?: false
    }
    val darkMode: Flow<Boolean> = context.dataStore.data.map {
        it[PreferencesKeys.DARK_MODE] ?: false
    }

    val biometricAuth: Flow<Boolean> = context.dataStore.data.map {
        it[PreferencesKeys.BIOMETRIC_AUTH] ?: false
    }

    suspend fun setDynamicColor(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.DYNAMIC_COLOR] = enabled
        }
    }
    suspend fun setDarkMode(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.DARK_MODE] = enabled
        }
    }

    suspend fun setBiometricAuth(enabled: Boolean){
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.BIOMETRIC_AUTH] = enabled
        }
    }

}