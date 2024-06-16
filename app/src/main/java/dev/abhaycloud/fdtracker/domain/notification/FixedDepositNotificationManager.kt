package dev.abhaycloud.fdtracker.domain.notification

import dev.abhaycloud.fdtracker.data.alarm.AlarmScheduler
import java.util.Date
import javax.inject.Inject

class FixedDepositNotificationManager @Inject constructor(private val alarmScheduler: AlarmScheduler) {

    fun scheduleNotification(fdId: Int, title: String, message: String, date: Date, daysBefore: Int) {
        alarmScheduler.scheduleAlarm(fdId, title, message, date.time, daysBefore)
    }

    fun cancelNotification(fdId: Int) {
        alarmScheduler.cancelAlarm(fdId)
    }
}