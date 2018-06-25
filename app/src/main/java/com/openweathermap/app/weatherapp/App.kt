package com.openweathermap.app.weatherapp

import android.app.Application
import com.openweathermap.app.weatherapp.di.DaggerAppComponent

class App : Application() {

    val appComponent = DaggerAppComponent.builder().build()

    override fun onCreate() {
        super.onCreate()
    }
}