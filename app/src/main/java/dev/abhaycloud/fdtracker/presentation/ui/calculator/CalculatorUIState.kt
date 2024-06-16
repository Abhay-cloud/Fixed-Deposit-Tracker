package dev.abhaycloud.fdtracker.presentation.ui.calculator

data class CalculatorUIState(
    val principalAmount: Int = 1,
    val annualInterestRate: Double = 1.0,
    val period: Int = 1,
    val maturityAmount: Double = 0.0
)
