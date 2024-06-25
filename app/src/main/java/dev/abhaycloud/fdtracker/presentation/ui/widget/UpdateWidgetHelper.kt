package dev.abhaycloud.fdtracker.presentation.ui.widget

import android.content.Context
import android.util.Log
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateWidgetHelper @Inject constructor(
    @ApplicationContext private val context: Context,
    private val fixedDepositWidget: GlanceAppWidget
) {
    private val coroutineScope = MainScope()

    fun updateWidget() {
        coroutineScope.launch {
            val fixedDepositWidgetGlanceId =
                GlanceAppWidgetManager(context).getGlanceIds(FixedDepositWidget::class.java)
                    .firstOrNull()
            fixedDepositWidgetGlanceId?.let {
                fixedDepositWidget.update(context, it)
            }
        }
    }
}