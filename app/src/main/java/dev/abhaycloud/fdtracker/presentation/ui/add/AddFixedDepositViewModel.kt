package dev.abhaycloud.fdtracker.presentation.ui.add

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.abhaycloud.fdtracker.domain.model.FixedDeposit
import dev.abhaycloud.fdtracker.domain.notification.FixedDepositNotificationManager
import dev.abhaycloud.fdtracker.domain.usecase.AddFixedDepositUseCase
import dev.abhaycloud.fdtracker.domain.usecase.DeleteFixedDepositUseCase
import dev.abhaycloud.fdtracker.domain.usecase.UpdateFixedDepositUseCase
import dev.abhaycloud.fdtracker.utils.DateUtils.toDateString
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddFixedDepositViewModel @Inject constructor(
    private val addFixedDepositUseCase: AddFixedDepositUseCase,
    private val updateFixedDepositUseCase: UpdateFixedDepositUseCase,
    private val deleteFixedDepositUseCase: DeleteFixedDepositUseCase,
    private val notificationManager: FixedDepositNotificationManager
) : ViewModel() {

    private val _fixedDeposit = MutableStateFlow<FixedDeposit?>(null)
    val fixedDeposit: StateFlow<FixedDeposit?> = _fixedDeposit

    private val _uiState = MutableStateFlow(
        AddFixedDepositUIState(
            bankName = "",
            bankNameError = null,
            principleAmount = _fixedDeposit.value?.principalAmount?.toString() ?: "",
            principleAmountError = null,
            maturityAmount = _fixedDeposit.value?.maturityAmount?.toString() ?: "",
            maturityAmountError = null,
            annualInterestRate = _fixedDeposit.value?.interestRate?.toString() ?: "",
            annualInterestRateError = null,
            startDate = _fixedDeposit.value?.startDate?.time?.toDateString() ?: "",
            startDateError = null,
            maturityDate = _fixedDeposit.value?.maturityDate?.time?.toDateString() ?: "",
            maturityDateError = null,
            notes = _fixedDeposit.value?.notes ?: ""
        )
    )
    val uiState: StateFlow<AddFixedDepositUIState> = _uiState

    fun setFixedDeposit(fixedDeposit: FixedDeposit) {
        _fixedDeposit.value = fixedDeposit
        _uiState.value = _uiState.value.copy(
            principleAmount = fixedDeposit.principalAmount.toString(),
            maturityAmount = fixedDeposit.maturityAmount.toString(),
            annualInterestRate = fixedDeposit.interestRate.toString(),
            startDate = fixedDeposit.startDate.time.toDateString(),
            maturityDate = fixedDeposit.maturityDate.time.toDateString(),
            notes = fixedDeposit.notes ?: ""
        )
    }

    fun onBankNameChange(name: String) {
        _uiState.update {  it.copy(bankName = name) }
        isValidBankName()
    }

    private fun isValidBankName(): Boolean {
        _uiState.update {  it.copy(bankNameError = if (!_uiState.value.bankName.isValidText()) "Enter a bank name" else null) }
        return _uiState.value.bankNameError == null
    }

    fun onPrincipleAmountChange(amount: String) {
        _uiState.update {  it.copy(principleAmount = amount) }
        isValidPrincipleAmount()
    }

    private fun isValidPrincipleAmount(): Boolean {
        _uiState.update {  it.copy(principleAmountError = if (!_uiState.value.principleAmount.isValidNumber()) "Enter a valid amount" else null) }
        return _uiState.value.principleAmountError == null
    }

    fun onMaturityAmountChange(amount: String) {
        _uiState.update {  it.copy(maturityAmount = amount) }
        isValidMaturityAmount()
    }

    private fun isValidMaturityAmount(): Boolean {
        _uiState.update {  it.copy(maturityAmountError = if (!_uiState.value.maturityAmount.isValidNumber()) "Enter a valid amount" else null) }
        return _uiState.value.maturityAmountError == null
    }

    fun onAnnualInterestChange(amount: String) {
        _uiState.update {  it.copy(annualInterestRate = amount) }
        isValidAnnualInterest()
    }

    private fun isValidAnnualInterest(): Boolean {
        _uiState.update {  it.copy(annualInterestRateError = if (!_uiState.value.annualInterestRate.isValidNumber()) "Enter a valid amount" else null) }
        return _uiState.value.annualInterestRateError == null
    }

    fun onStartDateChange(date: String) {
        _uiState.update {  it.copy(startDate = date) }
        isValidStartDate()
    }

    private fun isValidStartDate(): Boolean {
        _uiState.update {  it.copy(startDateError = if (!_uiState.value.startDate.isValidText()) "Select a start date" else null) }
        return _uiState.value.startDateError == null
    }

    fun onMaturityDateChange(date: String) {
        _uiState.update {  it.copy(maturityDate = date) }
        isValidMaturityDate()
    }

    private fun isValidMaturityDate(): Boolean {
        _uiState.update {  it.copy(maturityDateError = if (!_uiState.value.maturityDate.isValidText()) "Select a maturity date" else null) }
        return _uiState.value.maturityDateError == null
    }

    fun onNoteChange(note: String) {
        _uiState.update {  it.copy(notes = note) }
    }


//    fun validateFields(): Boolean {
//        return isValidBankName() && isValidPrincipleAmount() && isValidMaturityAmount() && isValidAnnualInterest() && isValidStartDate() && isValidMaturityDate()
//    }

    fun validateFields(): Boolean {
        val bankNameValid = isValidBankName()
        val principleAmountValid = isValidPrincipleAmount()
        val maturityAmountValid = isValidMaturityAmount()
        val annualInterestValid = isValidAnnualInterest()
        val startDateValid = isValidStartDate()
        val maturityDateValid = isValidMaturityDate()
        return bankNameValid && principleAmountValid && maturityAmountValid && annualInterestValid && startDateValid && maturityDateValid
    }

    private fun String.isValidText(): Boolean {
        return this.isNotBlank()
    }

    private fun String.isValidNumber(): Boolean {
        return this.toDoubleOrNull() != null
    }

    private var addFixedDepositJob: Job? = null
    private var updateFixedDepositJob: Job? = null
    private var deleteFixedDepositJob: Job? = null

    fun addFixedDeposit(fixedDeposit: FixedDeposit) {
        if (addFixedDepositJob?.isActive == true) return
        addFixedDepositJob = viewModelScope.launch {
            try {
                val fdID = addFixedDepositUseCase.execute(fixedDeposit)
                val updatedFixedDeposit = fixedDeposit.copy(id = fdID.toInt())
                scheduleNotification(updatedFixedDeposit)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                addFixedDepositJob = null
            }
        }
    }

    fun updateFixedDeposit(fixedDeposit: FixedDeposit) {
        if (updateFixedDepositJob?.isActive == true) return
        updateFixedDepositJob = viewModelScope.launch {
            try {
                updateFixedDepositUseCase.execute(fixedDeposit)
                notificationManager.cancelNotification(fixedDeposit.id)
                scheduleNotification(fixedDeposit)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                updateFixedDepositJob = null
            }
        }
    }

    fun deleteFixedDeposit(fixedDepositID: Int) {
        if (deleteFixedDepositJob?.isActive == true) return
        deleteFixedDepositJob = viewModelScope.launch {
            try {
                deleteFixedDepositUseCase.execute(fixedDepositID)
                notificationManager.cancelNotification(fixedDepositID)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                deleteFixedDepositJob = null
            }
        }
    }

    private fun scheduleNotification(fixedDeposit: FixedDeposit) {
        val title = "Fixed Deposit Maturity"
        val message = "Your fixed deposit of ${fixedDeposit.principalAmount} is maturing today."
        notificationManager.scheduleNotification(
            fixedDeposit.id,
            title,
            message,
            fixedDeposit.maturityDate,
            0
        )

        val beforeMaturityTitle = "Fixed Deposit Maturity"
        val beforeMaturityMessage =
            "Your fixed deposit of ${fixedDeposit.principalAmount} is maturing on ${fixedDeposit.maturityDate.time.toDateString()}."
        notificationManager.scheduleNotification(
            fixedDeposit.id,
            beforeMaturityTitle,
            beforeMaturityMessage,
            fixedDeposit.maturityDate,
            3
        )
    }


    override fun onCleared() {
        super.onCleared()
        Log.d("myapp", "viewmodel is cleared")
    }

}