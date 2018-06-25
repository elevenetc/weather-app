package com.openweathermap.app.weatherapp.di

import com.openweathermap.app.weatherapp.common.scheduling.Schedulers
import com.openweathermap.app.weatherapp.common.scheduling.SchedulersImpl
import com.openweathermap.app.weatherapp.logs.Logger
import com.openweathermap.app.weatherapp.logs.LoggerImpl
import dagger.Module
import dagger.Provides

@Module
class AppModule {
    @Provides
    fun schedulers(inst: SchedulersImpl): Schedulers = inst

    @Provides
    fun logger(inst: LoggerImpl): Logger = inst
}