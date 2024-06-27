package dev.abhaycloud.fdtracker.presentation.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import dagger.hilt.android.AndroidEntryPoint
import dev.abhaycloud.fdtracker.domain.usecase.RescheduleAlarmUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FixedDepositBootReceiver: BroadcastReceiver() {

    @Inject
    lateinit var rescheduleAlarmUseCase: RescheduleAlarmUseCase

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            Toast.makeText(context, "Rescheduling Alarms", Toast.LENGTH_SHORT).show()
            coroutineScope.launch {
                rescheduleAlarmUseCase.execute()
            }
        }
    }
}