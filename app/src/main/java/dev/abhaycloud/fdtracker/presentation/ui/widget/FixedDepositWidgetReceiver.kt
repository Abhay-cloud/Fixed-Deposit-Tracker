package dev.abhaycloud.fdtracker.presentation.ui.widget

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FixedDepositWidgetReceiver : GlanceAppWidgetReceiver() {
    @Inject
    lateinit var viewModel: FixedDepositWidgetViewModel
    override val glanceAppWidget: GlanceAppWidget get() = FixedDepositWidget(viewModel)

    private val coroutineScope = MainScope()

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        Log.d("myapp", "in the onReceive")
        if (intent.action == FixedDepositRefreshCallback.UPDATE_ACTION) {
//            UpdateWidgetHelper(context, glanceAppWidget).updateWidget()
            coroutineScope.launch {
                val fixedDepositWidgetGlanceId =
                    GlanceAppWidgetManager(context).getGlanceIds(FixedDepositWidget::class.java)
                        .firstOrNull()
                fixedDepositWidgetGlanceId?.let {
                    glanceAppWidget.update(context, it)
                }
            }
        }
    }

}