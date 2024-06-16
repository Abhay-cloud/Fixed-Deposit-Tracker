package dev.abhaycloud.fdtracker.presentation.ui.settings

import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SettingsScreen(
    viewModel: ThemeViewModel = hiltViewModel(),
    settingScreenViewModel: SettingScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val dynamicColor by viewModel.dynamicColor.collectAsState()
    val darkMode by viewModel.darkMode.collectAsState()

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
            onSwitchChanged = {
                viewModel.setDarkMode(it)
            })
        Spacer(modifier = Modifier.height(16.dp))
        SettingsItem(
            optionName = "Dynamic Color",
            isSwitch = true,
            switchValue = dynamicColor,
            onSwitchChanged = {
                viewModel.setDynamicColor(it)
            })
        Spacer(modifier = Modifier.height(16.dp))
        SettingsItem(optionName = "Delete All FDs", optionIcon = Icons.Outlined.Delete) {
            settingScreenViewModel.deleteAllFixedDeposits()
            Toast.makeText(
                context,
                "All the FDs have been deleted successfully.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

@Composable
fun SettingsItem(
    optionName: String,
    optionIcon: ImageVector? = null,
    isSwitch: Boolean = false,
    switchValue: Boolean = false,
    onSwitchChanged: (Boolean) -> Unit = {},
    onOptionClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .clickable {
                if (!isSwitch) {
                    onOptionClick()
                }
            }
            .fillMaxWidth(),
        shape = RoundedCornerShape(15.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                optionName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
            )
            if (isSwitch) {
                Switch(
                    checked = switchValue,
                    onCheckedChange = { onSwitchChanged.invoke(it) }
                )
            } else {
                Image(
                    imageVector = optionIcon!!,
                    contentDescription = "icon",
                    modifier = Modifier.size(25.dp)
                )
            }
        }
    }
}