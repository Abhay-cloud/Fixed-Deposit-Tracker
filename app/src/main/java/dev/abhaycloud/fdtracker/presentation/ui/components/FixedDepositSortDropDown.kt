package dev.abhaycloud.fdtracker.presentation.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.abhaycloud.fdtracker.presentation.ui.home.SortingOptions

@Composable
fun FixedDepositSortDropDown(
    expanded: Boolean,
    selectedOption: SortingOptions,
    onDismiss: () -> Unit,
    onOptionChange: (SortingOptions) -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onDismiss() }
    ) {
        DropdownMenuItem(onClick = {
            onOptionChange.invoke(SortingOptions.CLEAR)
        }, text = {
            Text("Clear")
        }, trailingIcon = {
            if (selectedOption == SortingOptions.CLEAR) {
                Icon(imageVector = Icons.Outlined.Check, contentDescription = "selected")
            }
        })
        DropdownMenuItem(onClick = {
            onOptionChange.invoke(SortingOptions.START_DATE_ASC)
        }, text = {
            Text("Start Date")
        }, trailingIcon = {
            if (selectedOption == SortingOptions.START_DATE_ASC) {
                Icon(imageVector = Icons.Outlined.Check, contentDescription = "selected")
            }
        })
        DropdownMenuItem(onClick = {
            onOptionChange.invoke(SortingOptions.MATURITY_DATE_DESC)
        }, text = {
            Text("Maturity Date")
        }, trailingIcon = {
            if (selectedOption == SortingOptions.MATURITY_DATE_DESC) {
                Icon(imageVector = Icons.Outlined.Check, contentDescription = "selected")
            }
        })
    }
}
