package dev.abhaycloud.fdtracker.utils

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateUtils {
    private val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())

    fun Long.toDateString(): String {
        return dateFormat.format(this)
    }

    fun Long.getDifferenceBetweenDays(date: Long): Int {
        return ((this - date) / (1000 * 60 * 60 * 24)).toInt()
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

}