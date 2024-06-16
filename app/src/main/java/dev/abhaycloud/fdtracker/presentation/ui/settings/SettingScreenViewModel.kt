package dev.abhaycloud.fdtracker.presentation.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.abhaycloud.fdtracker.domain.usecase.DeleteAllFixedDepositsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingScreenViewModel @Inject constructor(private val deleteAllFixedDepositsUseCase: DeleteAllFixedDepositsUseCase): ViewModel() {
    fun deleteAllFixedDeposits() {
        viewModelScope.launch {
            deleteAllFixedDepositsUseCase.execute()
        }
    }
}