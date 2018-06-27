package com.openweathermap.app.weatherapp.data

import android.arch.persistence.room.TypeConverter
import com.openweathermap.app.weatherapp.recent.SearchQuery
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

    @TypeConverter
    @JvmStatic
    fun toSearchQueryType(id: Int): SearchQuery.Type {
        return SearchQuery.Type.fromId(id)
    }

    @TypeConverter
    @JvmStatic
    fun fromSearchQueryType(type: SearchQuery.Type): Int {
        return type.id
    }

}