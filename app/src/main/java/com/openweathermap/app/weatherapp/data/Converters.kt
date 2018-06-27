package com.openweathermap.app.weatherapp.data

import android.arch.persistence.room.TypeConverter
import java.util.*

object Converters {

    @TypeConverter
    @JvmStatic
    fun toDate(dateLong: Long): Date {
        return Date(dateLong)
    }

    @TypeConverter
    @JvmStatic
    fun fromDate(date: Date): Long {
        return date.time
    }

}