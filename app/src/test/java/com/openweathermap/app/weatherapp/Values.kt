package com.openweathermap.app.weatherapp

import com.openweathermap.app.weatherapp.common.Weather

object Values {
    const val TEMPERATURE = 10f
    const val LOC_NAME = "London"
    val WEATHER = Weather(LOC_NAME, TEMPERATURE)
}