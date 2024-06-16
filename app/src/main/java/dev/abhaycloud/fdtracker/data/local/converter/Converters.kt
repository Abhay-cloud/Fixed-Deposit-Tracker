package dev.abhaycloud.fdtracker.data.local.converter

import androidx.room.TypeConverter
import java.util.Date

class Converters {
    @TypeConverter
    fun toTimeStamp(date: Date): Long = date.time

    @TypeConverter
    fun toDate(timeStamp: Long): Date = Date(timeStamp)
}