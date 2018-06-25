package com.openweathermap.app.weatherapp.logs

import javax.inject.Inject

class LoggerImpl @Inject constructor() : Logger {
    override fun log(throwable: Throwable) {
        throwable.printStackTrace()
    }
}