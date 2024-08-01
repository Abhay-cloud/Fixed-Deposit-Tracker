package dev.abhaycloud.fdtracker.presentation.ui.settings

import android.content.Intent
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.abhaycloud.fdtracker.R
import dev.abhaycloud.fdtracker.domain.model.biometrics.BiometricCheckResult
import dev.abhaycloud.fdtracker.presentation.ui.BiometricViewModel
import dev.abhaycloud.fdtracker.presentation.ui.components.ImageWrapper

@Composable
fun SettingsScreen(
    viewModel: ThemeViewModel = hiltViewModel(),
    biometricAuthViewModel: BiometricViewModel = hiltViewModel(),
    settingScreenViewModel: SettingScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val dynamicColor by viewModel.dynamicColor.collectAsState()
    val darkMode by viewModel.darkMode.collectAsState()
    val biometricAvailability by biometricAuthViewModel.biometricAvailability.collectAsState()
    val biometricAuth by biometricAuthViewModel.biometricAuthFlow.collectAsState()
    var showDeleteDialog by rememberSaveable {
        mutableStateOf(false)
    }
    val exportedFileUri by settingScreenViewModel.exportedFileUri.collectAsState()

    val darkModeChange: (Boolean) -> Unit = remember(viewModel) {
        {
            viewModel.setDarkMode(it)
        }
    }

    val dynamicColorChange: (Boolean) -> Unit = remember(viewModel) {
        {
            viewModel.setDynamicColor(it)
        }
    }

    val biometricAuthChange:(Boolean) -> Unit = remember(biometricAuthViewModel){

        {
            biometricAuthViewModel.setBiometricAuth(it)
        }

    }

    val exportData: () -> Unit = remember {
        {
            settingScreenViewModel.exportData()
        }
    }

    LaunchedEffect(key1 = exportedFileUri) {
        exportedFileUri?.let {
            Toast.makeText(context, "Data has been exported successfully!", Toast.LENGTH_SHORT)
                .show()
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(it, "text/csv")
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            try {
                context.startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(
                    context,
                    "Unable to open CSV file. Make sure that you have a proper CSV viewer.",
                    Toast.LENGTH_LONG
                ).show()
            }
            settingScreenViewModel.clearUri()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Settings", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(16.dp))

        SettingsItem(
            optionName = "Dark Mode",
            isSwitch = true,
            switchValue = darkMode,
            onSwitchChanged = darkModeChange
        )
        Spacer(modifier = Modifier.height(16.dp))
        SettingsItem(
            optionName = "Dynamic Color",
            isSwitch = true,
            switchValue = dynamicColor,
            onSwitchChanged = dynamicColorChange
        )
        if (biometricAvailability is BiometricCheckResult.Available){
            Spacer(modifier = Modifier.height(16.dp))
            SettingsItem(
                optionName = "Biometric Authentication",
                isSwitch = true,
                switchValue = biometricAuth,
                onSwitchChanged = biometricAuthChange
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        SettingsItem(optionName = "Delete All FDs", optionIcon = Icons.Outlined.Delete,)
        {
            showDeleteDialog = true
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (VERSION.SDK_INT >= VERSION_CODES.Q) {
            SettingsItem(
                optionName = "Export Data",
                optionDrawable = R.drawable.outline_download_for_offline_24,
                onOptionClick = exportData
            )
        }
    }


    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Are you sure you want to delete all Fixed Deposits?") },
            text = { Text("This action cannot be undone") },
            confirmButton = {
                TextButton(onClick = {
                    settingScreenViewModel.deleteAllFixedDeposits()
                    showDeleteDialog = false
                    Toast.makeText(
                        context,
                        "All the FDs have been deleted successfully.",
                        Toast.LENGTH_SHORT
                    ).show()
                }) {
                    Text("Delete it")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            },
        )
    }

}

@Composable
fun SettingsItem(
    optionName: String,
    optionIcon: ImageVector? = null,
    optionDrawable: Int? = null,
    isSwitch: Boolean = false,
    switchValue: Boolean = false,
    onSwitchChanged: (Boolean) -> Unit = {},
    onOptionClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .clickable(enabled = !isSwitch) {
                if (!isSwitch) {
                    onOptionClick()
                }
            }
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 16.dp, vertical = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                optionName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            if (isSwitch) {
                Switch(
                    checked = switchValue,
                    onCheckedChange = { onSwitchChanged.invoke(it) },
                    modifier = Modifier.height(20.dp)
                )
            } else {
                if (optionIcon != null) {
                    Image(
                        imageVector = optionIcon,
                        contentDescription = "icon",
                        modifier = Modifier.size(28.dp),
                        colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onPrimaryContainer)
                    )
                } else {
                    ImageWrapper(
                        resource = optionDrawable!!,
                        modifier = Modifier.size(28.dp),
                        colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onPrimaryContainer)
                    )
                }
            }
        }
    }
}