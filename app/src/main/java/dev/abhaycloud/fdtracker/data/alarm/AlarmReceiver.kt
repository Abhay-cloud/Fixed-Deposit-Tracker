package dev.abhaycloud.fdtracker.data.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let { context ->
            intent?.let {
                val notificationHelper = NotificationHelper(context)
                val title = it.getStringExtra("title")
                val message = it.getStringExtra("message")
                val fdID = it.getIntExtra("fdID", 0)
                Log.d("myapp", "onReceive: $fdID")
                notificationHelper.sendNotification(title, message, fdID)
            }
        }
    }
}