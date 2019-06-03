package com.donali.dateexample

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class TimestampConrverter {

    companion object {
        val dateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy")

        @TypeConverter
        @JvmStatic
        fun fromTimeStamp(value: String): Date {
            return   dateFormat.parse(value)
        }
        @TypeConverter
        @JvmStatic
        fun toTimeStamp(value: Date): String =
            value.toString()

    }
}