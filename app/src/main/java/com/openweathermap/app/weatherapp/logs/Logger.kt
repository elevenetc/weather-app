package com.openweathermap.app.weatherapp.logs

interface Logger {
    fun log(throwable: Throwable)
}