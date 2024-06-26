package dev.abhaycloud.fdtracker.presentation.ui.widget

import android.content.Context
import android.util.Log
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.Preferences
import androidx.glance.ColorFilter
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.color.ColorProvider
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.hilt.navigation.compose.hiltViewModel
import dev.abhaycloud.fdtracker.R
import dev.abhaycloud.fdtracker.domain.usecase.GetTotalInvestedAmountUseCase
import dev.abhaycloud.fdtracker.utils.Utils.toIndianFormat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class FixedDepositWidget(private var viewModel: FixedDepositWidgetViewModel) : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            var investedAmount by remember {
                mutableDoubleStateOf(0.0)
            }
            var maturityAmount by remember {
                mutableDoubleStateOf(0.0)
            }
            LaunchedEffect(key1 = Unit) {
                viewModel.getTotalInvestedAmount.collect {
                    investedAmount = it
                    Log.d("myapp", "Invested Amount: $it")
                }
            }
            LaunchedEffect(key1 = Unit) {
                viewModel.getTotalMaturityAmount.collect {
                    maturityAmount = it
                    Log.d("myapp", "Maturity Amount: $it")
                }
            }
            FixedDepositGlance(investedAmount, maturityAmount)
        }
    }

}

@Composable
private fun FixedDepositGlance(investedAmount: Double, maturityAmount: Double) {
    Box(
        modifier = GlanceModifier.fillMaxWidth().background(Color(0xff001F3F)).padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = GlanceModifier.fillMaxWidth(), contentAlignment = Alignment.TopEnd) {
            Image(
                provider = ImageProvider(R.drawable.baseline_refresh_24),
                contentDescription = "refresh",
                colorFilter = ColorFilter.tint(
                    ColorProvider(
                        day = Color(0xff787878),
                        night = Color(0XFFCDD5EA)
                    )
                ),
                modifier = GlanceModifier
                    .clickable(
                        actionRunCallback<FixedDepositRefreshCallback>()
                    )
            )
        }
        Column(modifier = GlanceModifier.fillMaxWidth()) {
            Row {
                Text(
                    text = "Invested Amount:",
                    style = TextStyle(
                        color = ColorProvider(
                            day = Color(0xff787878),
                            night = Color(0XFFCDD5EA)
                        ),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            Text(
                text = "₹${investedAmount.toIndianFormat(includeDecimal = true)}",
                style = TextStyle(
                    color = ColorProvider(
                        day = Color.White,
                        night = Color(0XFFCDD5EA)
                    ),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = GlanceModifier.height(5.dp))
            Text(
                text = "Maturity Amount:",
                style = TextStyle(
                    color = ColorProvider(
                        day = Color(0xff787878),
                        night = Color(0XFFCDD5EA)
                    ),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = "₹${maturityAmount.toIndianFormat(includeDecimal = true)}",
                style = TextStyle(
                    color = ColorProvider(
                        day = Color.White,
                        night = Color(0XFFCDD5EA)
                    ),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }

}
