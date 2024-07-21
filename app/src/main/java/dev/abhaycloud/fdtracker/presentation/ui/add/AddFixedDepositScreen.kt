package dev.abhaycloud.fdtracker.presentation.ui.add

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import dev.abhaycloud.fdtracker.domain.model.FixedDeposit
import dev.abhaycloud.fdtracker.presentation.ui.components.FixedDepositDatePickerDialog
import dev.abhaycloud.fdtracker.presentation.ui.components.FixedDepositField
import dev.abhaycloud.fdtracker.utils.DateUtils.getDifferenceBetweenDays
import java.util.Date

@Composable
fun AddFixedDepositScreen(
    navController: NavController,
    fixedDeposit: FixedDeposit? = null,
    onSaved: () -> Unit = {},
    onBackPressed: () -> Unit = {}
) {
    val viewModel: AddFixedDepositViewModel = hiltViewModel()
    val context = LocalContext.current

    val uiState by viewModel.uiState.collectAsState()

    var isMatured by rememberSaveable {
        mutableStateOf(
            false
        )
    }

    var showDeleteDialog by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = Unit) {
        fixedDeposit?.let {
            viewModel.setFixedDeposit(it)
            isMatured = System.currentTimeMillis() > it.maturityDate.time
        }
    }

    var showInitialDatePicker by rememberSaveable {
        mutableStateOf(false)
    }

    var showMaturityDatePicker by rememberSaveable {
        mutableStateOf(false)
    }

    var startDateObj by rememberSaveable {
        mutableStateOf(fixedDeposit?.startDate ?: Date())
    }

    var endDateObj by rememberSaveable {
        mutableStateOf(fixedDeposit?.maturityDate ?: Date())
    }

    val updateBankName: (String) -> Unit = remember {
        {
            viewModel.onBankNameChange(it)
        }
    }

    val updatePrincipleAmount: (String) -> Unit = remember {
        {
            viewModel.onPrincipleAmountChange(it)
        }
    }

    val updateMaturityAmount: (String) -> Unit = remember {
        {
            viewModel.onMaturityAmountChange(it)
        }
    }

    val updateAnnualInterest: (String) -> Unit = remember {
        {
            viewModel.onAnnualInterestChange(it)
        }
    }

    val updateNotes: (String) -> Unit = remember {
        {
            viewModel.onNoteChange(it)
        }
    }

    val deleteDialog: () -> Unit = remember {
        {
            showDeleteDialog = true
        }
    }

    val addUpdateFD: () -> Unit = remember {
        {
                if (viewModel.validateFields()) {
                    if (fixedDeposit == null) {
                        viewModel.addFixedDeposit(
                            FixedDeposit(
                                0,
                                uiState.bankName,
                                uiState.principleAmount.toDouble(),
                                uiState.maturityAmount.toDouble(),
                                endDateObj.time.getDifferenceBetweenDays(startDateObj.time),
                                uiState.annualInterestRate.toDouble(),
                                startDateObj,
                                endDateObj,
                                Date(),
                                uiState.notes
                            )
                        )
                    } else {
                        viewModel.updateFixedDeposit(
                            FixedDeposit(
                                fixedDeposit.id,
                                uiState.bankName,
                                uiState.principleAmount.toDouble(),
                                uiState.maturityAmount.toDouble(),
                                endDateObj.time.getDifferenceBetweenDays(startDateObj.time),
                                uiState.annualInterestRate.toDouble(),
                                startDateObj,
                                endDateObj,
                                fixedDeposit.createdAt,
                                uiState.notes
                            )
                        )
                    }
                    Toast.makeText(
                        context,
                        "Fixed deposit ${if (fixedDeposit == null) "added" else "updated"} successfully!",
                        Toast.LENGTH_SHORT
                    ).show()
                    onSaved()
                    navController.popBackStack()
                }
        }
    }

    BackHandler {
        navController.popBackStack()
        onBackPressed()
    }

    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollState)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Add fixed deposit", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(16.dp))
        FixedDepositField(
            title = "Bank Name",
            value = uiState.bankName,
            readOnly = isMatured,
            isError = uiState.bankNameError != null,
            errorMessage = uiState.bankNameError,
            onValueChanged = updateBankName
        )
        Spacer(modifier = Modifier.height(16.dp))
        FixedDepositField(
            title = "Principle Amount",
            value = uiState.principleAmount,
            readOnly = isMatured,
            isNumericField = true,
            isError = uiState.principleAmountError != null,
            errorMessage = uiState.principleAmountError,
            onValueChanged = updatePrincipleAmount
        )
        Spacer(modifier = Modifier.height(16.dp))
        FixedDepositField(
            title = "Maturity Amount",
            value = uiState.maturityAmount,
            isNumericField = true,
            readOnly = isMatured,
            isError = uiState.maturityAmountError != null,
            errorMessage = uiState.maturityAmountError,
            onValueChanged = updateMaturityAmount
        )
        Spacer(modifier = Modifier.height(16.dp))
        FixedDepositField(
            title = "Annual Interest",
            value = uiState.annualInterestRate,
            isNumericField = true,
            readOnly = isMatured,
            isError = uiState.annualInterestRateError != null,
            errorMessage = uiState.annualInterestRateError,
            onValueChanged = updateAnnualInterest
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            FixedDepositField(
                modifier = Modifier.weight(1f),
                title = "Start Date",
                value = uiState.startDate,
                enabled = false,
                readOnly = isMatured,
                isError = uiState.startDateError != null,
                errorMessage = uiState.startDateError,
                onClick = {
                    showInitialDatePicker = true
                }
            ) {
            }
            Spacer(modifier = Modifier.width(8.dp))
            FixedDepositField(
                modifier = Modifier.weight(1f),
                title = "Maturity Date",
                value = uiState.maturityDate,
                enabled = false,
                readOnly = isMatured,
                isError = uiState.maturityDateError != null,
                errorMessage = uiState.maturityDateError,
                onClick = {
                    showMaturityDatePicker = true
                }
            ) {

            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        FixedDepositField(
            title = "Notes",
            value = uiState.notes,
            isMultipleLine = true,
            isLastField = true,
            readOnly = isMatured,
            onValueChanged = updateNotes
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            if (fixedDeposit != null) {
                Button(modifier = Modifier.weight(1f), onClick = deleteDialog
//                {
//                    scope.launch {
//                        showDeleteDialog = true
//                    }
//                }
                ) {
                    Text(text = "Delete")
                }
                Spacer(modifier = Modifier.width(16.dp))
            }
            if (!isMatured) {
                Button(modifier = Modifier.weight(1f), onClick = addUpdateFD) {
                    Text(text = if (fixedDeposit == null) "Save" else "Update")
                }
            }
        }
    }

    if (showInitialDatePicker) {
        FixedDepositDatePickerDialog(
            isStartDate = true,
            onDateSelected = { dateString, date ->
                startDateObj = date
                showInitialDatePicker = false
                viewModel.onStartDateChange(dateString)
            }) {
            showInitialDatePicker = false
        }
    }

    if (showMaturityDatePicker) {
        FixedDepositDatePickerDialog(
            isStartDate = false,
            onDateSelected = { dateString, date ->
                endDateObj = date
                showMaturityDatePicker = false
                viewModel.onMaturityDateChange(dateString)
            }) {
            showMaturityDatePicker = false
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Are you sure you want to delete this Fixed Deposit?") },
            text = { Text("This action cannot be undone") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deleteFixedDeposit(fixedDeposit!!.id)
                    onSaved()
                    navController.popBackStack()
                }) {
                    Text("Delete it".uppercase())
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel".uppercase())
                }
            },
        )
    }

}


//@Preview
//@Composable
//fun FixedDepositFieldPreview() {
//    FixedDepositField(title = "Bank name", value = "") {
//
//    }
//}

//@Preview
//@Composable
//fun AddFixedDepositScreenPreview() {
//    AddFixedDepositScreen()
//}