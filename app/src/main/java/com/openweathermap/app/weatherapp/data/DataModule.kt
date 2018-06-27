package com.openweathermap.app.weatherapp.data

import android.arch.persistence.room.Room
import android.content.Context
import com.openweathermap.app.weatherapp.common.Names
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton


@Module
class DataModule {

    @Provides
    @Singleton
    fun database(appContext: Context, @Named(Names.DATABASE_NAME) name: String): Database {
        return Room.databaseBuilder(appContext, DatabaseImpl::class.java, name).build()
    }

    @Provides
    @Named(Names.DATABASE_NAME)
    fun databaseName(): String {
        return "weather-main.db"
    }
}