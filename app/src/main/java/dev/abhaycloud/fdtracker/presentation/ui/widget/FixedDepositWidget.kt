package dev.abhaycloud.fdtracker.presentation.ui.widget

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.color.ColorProvider
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class FixedDepositWidget: GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            FixedDepositGlance()
        }
    }

    @Composable
    private fun FixedDepositGlance() {
        val viewModel: FixedDepositWidgetViewModel = hiltViewModel()
        val totalInvestedAmount by viewModel.getTotalInvestedAmount.collectAsState()
        val totalMaturityAmount by viewModel.getTotalMaturityAmount.collectAsState()

//        val totalInvestedAmount = 0
//        val totalMaturityAmount = 0




        LaunchedEffect(key1 = Unit) {
            Log.d("myapp", "$totalMaturityAmount $totalInvestedAmount")
        }

        Box(
            modifier = GlanceModifier.fillMaxSize().background(Color.White).padding(16.dp)
        ) {
            Column {
                Text(
                    text = "Invested Amount: ${totalInvestedAmount}",
                    style = TextStyle(
                        color = ColorProvider(
                            day = Color(0XFF3A3F4E),
                            night = Color(0XFFCDD5EA)
                        ),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                ))
                Text(
                    text = "Maturity Amount: ${totalMaturityAmount}",
                    style = TextStyle(
                        color = ColorProvider(
                            day = Color(0XFF3A3F4E),
                            night = Color(0XFFCDD5EA)
                        ),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    ))
            }
        }

    }

}