package dev.abhaycloud.fdtracker.utils

import android.util.Log
import com.google.gson.Gson
import java.text.DecimalFormat
import java.util.Calendar
import java.util.Date

object Utils {
    fun Double.getFraction(completedDays: Double): Float {
        return (completedDays / this).toFloat()
    }

    inline fun <reified T> T.toJson(): String {
        return Gson().toJson(this)
    }

    inline fun <reified T> String.fromJson(): T {
        return Gson().fromJson(this, T::class.java)
    }

    fun Number.toIndianFormat(includeDecimal: Boolean = false): String {
        val numberFormat = DecimalFormat(if (includeDecimal) "##,##,###.00" else "##,##,###")
        return numberFormat.format(this)
    }

}