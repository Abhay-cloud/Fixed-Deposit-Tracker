package dev.abhaycloud.fdtracker.utils

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.round

object DateUtils {
    private val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())

    fun Long.toDateString(): String {
        return dateFormat.format(this)
    }

    fun Long.getDifferenceBetweenDays(date: Long): Int {
        return round((this - date).toDouble() / (1000 * 60 * 60 * 24)).toInt().takeIf { it > 0 } ?: 0
    }

    infix fun Long.isBefore(date: Long): Boolean {
        val date1 = Calendar.getInstance()
        date1.time = Date(this)
        val date2 = Calendar.getInstance()
        date2.time = Date(date)
        return date1 < date2
    }

    infix fun Long.isAfter(date: Long): Boolean {
        val date1 = Calendar.getInstance()
        date1.time = Date(this)
        val date2 = Calendar.getInstance()
        date2.time = Date(date)
        return date1 > date2
    }

    fun Date.toLocalDate(): LocalDate {
        return Instant.ofEpochMilli(this.time).atZone(ZoneId.systemDefault()).toLocalDate()
    }

}