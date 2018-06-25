package com.openweathermap.app.weatherapp.di

import com.openweathermap.app.weatherapp.common.scheduling.Schedulers
import com.openweathermap.app.weatherapp.logs.Logger
import com.openweathermap.app.weatherapp.search.SearchComponent
import com.openweathermap.app.weatherapp.weather.openweather.OpenWeatherApiModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, OpenWeatherApiModule::class])
interface AppComponent {
    fun schedulers(): Schedulers
    fun logger(): Logger

    fun searchComponent(): SearchComponent
}