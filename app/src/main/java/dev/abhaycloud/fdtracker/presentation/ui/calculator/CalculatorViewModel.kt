package dev.abhaycloud.fdtracker.presentation.ui.calculator

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlin.math.pow

@HiltViewModel
class CalculatorViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(
        CalculatorUIState(
            principalAmount = 1_00_000,
            annualInterestRate = 6.0,
            period = 5
        )
    )
    val uiState: StateFlow<CalculatorUIState> = _uiState

    private val _isSliderTabSelected = MutableStateFlow(true)
    val isSliderTabSelected: MutableStateFlow<Boolean> = _isSliderTabSelected

    init {
        calculateMaturityAmount()
    }

    fun onTabChange(isSliderTabSelected: Boolean) {
        _isSliderTabSelected.value = isSliderTabSelected
    }

    fun onPrincipalAmountChange(amount: Int) {
        _uiState.update {
            it.copy(principalAmount = if (amount > 10_00_000) 10_00_000 else if (amount <= 0) 1 else amount)
        }
        calculateMaturityAmount()
    }

    fun onAnnualInterestChange(amount: Double) {
        _uiState.update {
            it.copy(annualInterestRate = if (amount > 15.0) 15.0 else if (amount <= 0.0) 1.0 else amount)
        }
        calculateMaturityAmount()
    }

    fun onYearChange(year: Int) {
        _uiState.update {
            it.copy(period = if (year > 25) 25 else if (year <= 0) 1 else year)
        }
        calculateMaturityAmount()
    }

    private fun calculateMaturityAmount() {
        val p = _uiState.value.principalAmount.toDouble()
        val r = _uiState.value.annualInterestRate / 100
        val t = _uiState.value.period.toDouble()
        val n = 4 // Quarterly compounding

        val maturityAmount = p * (1 + r / n).pow(n * t)
        _uiState.update { it.copy(maturityAmount = maturityAmount) }
    }
}