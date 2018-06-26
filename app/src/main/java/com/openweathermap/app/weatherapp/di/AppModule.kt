package com.openweathermap.app.weatherapp.di

import android.content.Context
import com.openweathermap.app.weatherapp.common.logs.Logger
import com.openweathermap.app.weatherapp.common.logs.LoggerImpl
import com.openweathermap.app.weatherapp.common.permissions.PermissionsManager
import com.openweathermap.app.weatherapp.common.permissions.PermissionsManagerImpl
import com.openweathermap.app.weatherapp.common.scheduling.Schedulers
import com.openweathermap.app.weatherapp.common.scheduling.SchedulersImpl
import com.openweathermap.app.weatherapp.location.LocationModel
import com.openweathermap.app.weatherapp.location.LocationModelImpl
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val appContext: Context) {

    @Provides
    fun schedulers(inst: SchedulersImpl): Schedulers = inst

    @Provides
    fun logger(inst: LoggerImpl): Logger = inst

    @Provides
    fun permissions(inst: PermissionsManagerImpl): PermissionsManager = inst

    @Provides
    fun locations(inst: LocationModelImpl): LocationModel = inst

    @Provides
    fun appContext(): Context {
        return appContext
    }
}