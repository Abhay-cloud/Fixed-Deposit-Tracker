package dev.abhaycloud.fdtracker.data.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import javax.inject.Inject

class AlarmScheduler @Inject constructor(private val context: Context) {

    companion object {
        const val ALARM_TYPE_MATURITY = 0
        const val ALARM_TYPE_BEFORE_MATURITY = 1
    }

    fun scheduleAlarm(
        fixedDepositID: Int,
        title: String,
        message: String,
        maturityDate: Long,
        daysBefore: Int
    ) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("title", title)
            putExtra("message", message)
            putExtra("fdID", fixedDepositID)
        }
        scheduleMaturityAlarm(fixedDepositID, maturityDate, intent)
        scheduleBeforeMaturityDateAlarm(fixedDepositID, maturityDate, daysBefore, intent)
    }

    private fun scheduleMaturityAlarm(fixedDepositID: Int, maturityDate: Long, intent: Intent) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            fixedDepositID * 100 + ALARM_TYPE_MATURITY,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            maturityDate,
            pendingIntent
        )
    }

    private fun scheduleBeforeMaturityDateAlarm(
        fixedDepositID: Int,
        maturityDate: Long,
        daysBefore: Int,
        intent: Intent
    ) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val notifyTime = maturityDate - daysBefore * 24 * 60 * 60 * 1000
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            fixedDepositID * 100 + ALARM_TYPE_BEFORE_MATURITY,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, notifyTime, pendingIntent)
    }

    fun cancelAlarm(fixedDepositID: Int) {
        cancelMaturityAlarm(fixedDepositID)
        cancelBeforeMaturityAlarm(fixedDepositID)
    }

    private fun cancelMaturityAlarm(fixedDepositID: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            fixedDepositID * 100 + ALARM_TYPE_MATURITY,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }

    private fun cancelBeforeMaturityAlarm(fixedDepositID: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            fixedDepositID * 100 + ALARM_TYPE_BEFORE_MATURITY,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }
//    fun scheduleAlarm(fdId: Int, title: String, message: String, time: Long) {
//        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        Log.d("myapp", "scheduleAlarm: $fdId")
//        val intent = Intent(context, AlarmReceiver::class.java).apply {
//            putExtra("title", title)
//            putExtra("message", message)
//            putExtra("fdID", fdId)
//        }
//        val pendingIntent = PendingIntent.getBroadcast(
//            context,
//            fdId,
//            intent,
//            PendingIntent.FLAG_IMMUTABLE
//        )
//        alarmManager.setExactAndAllowWhileIdle(
//            AlarmManager.RTC_WAKEUP,
//            time,
//            pendingIntent
//        )
//    }
//
//    fun cancelAlarm(fdId: Int) {
//        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val intent = Intent(context, AlarmReceiver::class.java)
//        val pendingIntent = PendingIntent.getBroadcast(
//            context,
//            fdId,
//            intent,
//            PendingIntent.FLAG_UPDATE_CURRENT
//        )
//        alarmManager.cancel(pendingIntent)
//    }

}