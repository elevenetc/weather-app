package com.openweathermap.app.weatherapp.weather.openweather

import android.content.Context
import com.openweathermap.app.weatherapp.R
import com.openweathermap.app.weatherapp.common.Weather
import javax.inject.Inject

class OpenWeatherMapper @Inject constructor(private val appContext: Context) {
    fun toWeather(from: ApiWeather): Weather {

        var name = from.name

        if (name.isNullOrEmpty())
            name = appContext.getString(R.string.unknown_location_name)


        return Weather(name, from.main.temp, from.coord.lat, from.coord.lon)
    }
}