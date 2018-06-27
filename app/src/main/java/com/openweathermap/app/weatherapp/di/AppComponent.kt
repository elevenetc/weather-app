package com.openweathermap.app.weatherapp.di

import com.openweathermap.app.weatherapp.common.logs.Logger
import com.openweathermap.app.weatherapp.common.permissions.PermissionsManager
import com.openweathermap.app.weatherapp.common.scheduling.Schedulers
import com.openweathermap.app.weatherapp.data.DataModule
import com.openweathermap.app.weatherapp.data.Database
import com.openweathermap.app.weatherapp.search.SearchComponent
import com.openweathermap.app.weatherapp.weather.openweather.OpenWeatherApiModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, OpenWeatherApiModule::class, DataModule::class])
interface AppComponent {

    fun schedulers(): Schedulers
    fun logger(): Logger
    fun permissions(): PermissionsManager
    fun database(): Database

    fun searchComponent(): SearchComponent
}