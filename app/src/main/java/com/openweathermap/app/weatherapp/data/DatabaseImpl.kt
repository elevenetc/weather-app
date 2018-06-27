package com.openweathermap.app.weatherapp.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.openweathermap.app.weatherapp.recent.SearchQuery

@Database(entities = [SearchQuery::class], version = 1)
@TypeConverters(Converters::class)
abstract class DatabaseImpl : RoomDatabase(), com.openweathermap.app.weatherapp.data.Database