package dev.abhaycloud.fdtracker.presentation.ui.add

data class AddFixedDepositUIState(
    val bankName: String = "",
    val bankNameError: String? = null,
    val principleAmount: String = "",
    val principleAmountError: String? = null,
    val maturityAmount: String = "",
    val maturityAmountError: String? = null,
    val annualInterestRate: String = "",
    val annualInterestRateError: String? = null,
    val startDate: String = "",
    val startDateError: String? = null,
    val maturityDate: String = "",
    val maturityDateError: String? = null,
    val notes: String = ""
)