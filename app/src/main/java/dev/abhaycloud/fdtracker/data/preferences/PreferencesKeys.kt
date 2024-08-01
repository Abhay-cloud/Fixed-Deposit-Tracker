package dev.abhaycloud.fdtracker.data.preferences

import androidx.datastore.preferences.core.booleanPreferencesKey

object PreferencesKeys {
    val DYNAMIC_COLOR = booleanPreferencesKey("dynamic_color")
    val DARK_MODE = booleanPreferencesKey("dark_mode")
    val BIOMETRIC_AUTH = booleanPreferencesKey("is_biometric_auth_enabled")
}