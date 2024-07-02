package dev.abhaycloud.fdtracker.presentation.ui.settings

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.abhaycloud.fdtracker.domain.model.FixedDeposit
import dev.abhaycloud.fdtracker.domain.usecase.DeleteAllFixedDepositsUseCase
import dev.abhaycloud.fdtracker.domain.usecase.ExportFixedDepositUseCase
import dev.abhaycloud.fdtracker.presentation.ui.widget.UpdateWidgetHelper
import dev.abhaycloud.fdtracker.utils.DateUtils.toDateString
import dev.abhaycloud.fdtracker.utils.FileUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class SettingScreenViewModel @Inject constructor(
    private val deleteAllFixedDepositsUseCase: DeleteAllFixedDepositsUseCase,
    private val widgetHelper: UpdateWidgetHelper,
    private val exportFixedDepositUseCase: ExportFixedDepositUseCase,
    private val fileUtils: FileUtils
) : ViewModel() {

    private val _exportedFileUri = MutableStateFlow<Uri?>(null)
    val exportedFileUri = _exportedFileUri.asStateFlow()

    fun deleteAllFixedDeposits() {
        viewModelScope.launch {
            deleteAllFixedDepositsUseCase.execute()
            widgetHelper.updateWidget()
        }
    }

    fun exportData() {
        viewModelScope.launch {
            val csvData = exportFixedDepositUseCase.execute()
            val uri = fileUtils.saveFileToDownloads(csvData, "FixedDeposits_${Date().time.toDateString()}")
            _exportedFileUri.update {
                uri
            }
        }
    }

    fun clearUri() {
        _exportedFileUri.value = null
    }

}