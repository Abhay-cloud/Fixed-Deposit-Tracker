package dev.abhaycloud.fdtracker.presentation.ui.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import dev.abhaycloud.fdtracker.utils.DateUtils.isAfter
import dev.abhaycloud.fdtracker.utils.DateUtils.isBefore
import dev.abhaycloud.fdtracker.utils.DateUtils.toDateString
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FixedDepositDatePickerDialog(
    isStartDate: Boolean = true,
    onDateSelected: (String, Date) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialDisplayMode = DisplayMode.Picker,
//        initialSelectedDateMillis = Calendar.getInstance().timeInMillis,
    )

    DatePickerDialog(onDismissRequest = onDismiss, confirmButton = {
        TextButton(onClick = {
            var date = ""
            datePickerState.selectedDateMillis?.let {
                date = it.toDateString()
                onDateSelected.invoke(date, Date(it))
            }
        }) {
            Text(text = "Okay")
        }
    }) {
        DatePicker(state = datePickerState, dateValidator = {
            if (isStartDate) it isBefore Calendar.getInstance().timeInMillis else it isAfter Calendar.getInstance().timeInMillis
        })
    }

}
