package dev.abhaycloud.fdtracker.presentation.ui.widget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.abhaycloud.fdtracker.domain.usecase.GetTotalInvestedAmountUseCase
import dev.abhaycloud.fdtracker.domain.usecase.GetTotalMaturityAmountUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FixedDepositWidgetViewModel @Inject constructor(
    getTotalInvestedAmountUseCase: GetTotalInvestedAmountUseCase,
    getTotalMaturityAmountUseCase: GetTotalMaturityAmountUseCase
) : ViewModel() {
    val getTotalInvestedAmount: StateFlow<Double> =
        getTotalInvestedAmountUseCase.execute().stateIn(viewModelScope, SharingStarted.Eagerly, 0.0)
    val getTotalMaturityAmount: StateFlow<Double> =
        getTotalMaturityAmountUseCase.execute().stateIn(viewModelScope, SharingStarted.Eagerly, 0.0)
}