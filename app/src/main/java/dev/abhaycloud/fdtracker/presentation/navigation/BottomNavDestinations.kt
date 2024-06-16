package dev.abhaycloud.fdtracker.presentation.navigation

import dev.abhaycloud.fdtracker.R

sealed class BottomNavDestinations(val title: String, val route: String, val icon: Int) {
    object HomeScreen : BottomNavDestinations(title = "Home", route = "homeScreen", icon = R.drawable.outline_home_24)
    object CalculatorScreen : BottomNavDestinations(title = "Calculator", route = "calculatorScreen", icon = R.drawable.outline_calculate_24)
    object SettingsScreen : BottomNavDestinations(title = "Settings", route = "settingsScreen", icon = R.drawable.outline_settings_24)
}