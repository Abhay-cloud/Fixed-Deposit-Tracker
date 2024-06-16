package dev.abhaycloud.fdtracker

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import dagger.hilt.android.AndroidEntryPoint
import dev.abhaycloud.fdtracker.data.local.database.FixedDepositDatabase
import dev.abhaycloud.fdtracker.data.local.mapper.toEntity
import dev.abhaycloud.fdtracker.domain.model.FixedDeposit
import dev.abhaycloud.fdtracker.presentation.theme.FDTrackerTheme
import dev.abhaycloud.fdtracker.presentation.ui.FixedDepositApp
import dev.abhaycloud.fdtracker.presentation.ui.add.AddFixedDepositViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Date

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FDTrackerTheme {
                FixedDepositApp()
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