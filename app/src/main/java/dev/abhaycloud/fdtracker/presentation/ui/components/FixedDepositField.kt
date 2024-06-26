package dev.abhaycloud.fdtracker.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FixedDepositField(
    modifier: Modifier = Modifier,
    fieldModifier:  Modifier = Modifier,
    title: String,
    value: String,
    isNumericField: Boolean = false,
    isDecimalAllowed: Boolean = true,
    isMultipleLine: Boolean = false,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    isLastField: Boolean = false,
    errorMessage: String? = null,
    isError: Boolean = false,
    onClick: () -> Unit = {},
    onValueChanged: (String) -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            modifier = fieldModifier.fillMaxWidth().clickable(enabled = !readOnly) {
                onClick()
            },
            value = value,
            enabled = enabled,
            readOnly = readOnly,
            onValueChange = {
                if (isNumericField) {
                    if (isDecimalAllowed) {
                        val filteredValue = it.filter { it.isDigit() || it == '.' }
                        onValueChanged(filteredValue)
                    } else {
                        val filteredValue = it.filter { it.isDigit()}
                        onValueChanged(filteredValue)
                    }
                } else {
                    onValueChanged(it)
                }
            },
            maxLines = if (isMultipleLine) 4 else 1,
            singleLine = !isMultipleLine,
            isError = isError,
            keyboardOptions = KeyboardOptions(
                keyboardType = if (isNumericField) KeyboardType.Decimal else KeyboardType.Text,
                imeAction = if (isLastField) ImeAction.Done else ImeAction.Next
            ),
            supportingText = {
                if (isError) {
                    Text(text = errorMessage!!, fontSize = 13.sp, fontWeight = FontWeight.Normal)
                }
            }
        )
    }
}