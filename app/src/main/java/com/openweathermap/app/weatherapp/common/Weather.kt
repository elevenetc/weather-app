package com.openweathermap.app.weatherapp.common

data class Weather(
        val name: String = "",
        val temperature: Float = 0.0f,
        val lat: Double = 0.0,
        val lon: Double = 0.0
)