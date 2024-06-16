package dev.abhaycloud.fdtracker.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.abhaycloud.fdtracker.domain.model.FixedDeposit
import dev.abhaycloud.fdtracker.domain.usecase.GetAllFixedDepositUseCase
import dev.abhaycloud.fdtracker.domain.usecase.GetTotalInvestedAmountUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getAllFixedDepositUseCase: GetAllFixedDepositUseCase,
    private val getTotalInvestedAmountUseCase: GetTotalInvestedAmountUseCase
) : ViewModel() {

    private val _sortOption = MutableStateFlow(SortingOptions.CLEAR)
    val sortOption: StateFlow<SortingOptions> = _sortOption

    val getAllFixedDepositList: StateFlow<List<FixedDeposit>> = getAllFixedDepositUseCase.execute()
        .combine(_sortOption) { list, option ->
            when (option) {
                SortingOptions.CLEAR -> list.sortedByDescending { it.createdAt }
                SortingOptions.START_DATE_ASC -> list.sortedBy { it.startDate }
                SortingOptions.MATURITY_DATE_DESC -> list.sortedByDescending { it.maturityDate }
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val getTotalInvestedAmount: StateFlow<Double> =
        getTotalInvestedAmountUseCase.execute().stateIn(viewModelScope, SharingStarted.Lazily, 0.0)

    fun updateSortOrder(sortOption: SortingOptions) {
        _sortOption.value = sortOption
    }

//    init {
//        getAllFixedDeposit()
//    }

//    fun getAllFixedDeposit() {
//        viewModelScope.launch {
//            _getAllFixedDepositList.value = getAllFixedDepositUseCase.execute()
//        }
//    }
}