package dev.abhaycloud.fdtracker.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import dev.abhaycloud.fdtracker.presentation.theme.FDTrackerTheme
import dev.abhaycloud.fdtracker.presentation.ui.add.AddFixedDepositViewModel
import dev.abhaycloud.fdtracker.presentation.ui.settings.ThemeViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val shortcutId = intent.getStringExtra("shortcut_id")
        setContent {
            val viewModel: ThemeViewModel = hiltViewModel()
            val dynamicColor by viewModel.dynamicColor.collectAsState()
            val darkMode by viewModel.darkMode.collectAsState()
            FDTrackerTheme(
                darkTheme = darkMode, dynamicColor = dynamicColor
            ) {
                FixedDepositApp(shortcutId)
            }
        }
    }
}

@Composable
fun AddFixedDeposit(viewModel: AddFixedDepositViewModel = hiltViewModel()) {
    val context = LocalContext.current as MainActivity
    LaunchedEffect(key1 = Unit) {
        val intent = context.intent
        val shortcutId = intent.getStringExtra("shortcut_id")
        Log.d("myapp", "shortcutId id is $shortcutId")
    }
//    LaunchedEffect(key1 = Unit) {
//        val intent = context.intent
//        val fdID = intent.getIntExtra("fdID", -1)
//        Log.d("myapp", "From FD Notification, id is $fdID")
//    }
//    LaunchedEffect(key1 = Unit) {
//        val calendar =  Calendar.getInstance()
//        calendar.add(Calendar.SECOND, 20)
//        Log.d("myapp", "${Calendar.getInstance().time} Future Time is ${calendar.time}")
//        viewModel.addFixedDeposit(
//            FixedDeposit(
//                0,
//                5500.0,
//                19,
//                6.1,
//                Date(),
//                calendar.time,
//                "remind me"
//            )
//        )
//    }
}