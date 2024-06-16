package dev.abhaycloud.fdtracker.presentation.navigation

sealed class FixedDepositNavigationScreens(val route: String) {
    data object AddFixedDeposit: FixedDepositNavigationScreens(route = "addFixedDeposit")
    data object ViewFixedDeposit: FixedDepositNavigationScreens(route = "viewFixedDeposit")
}