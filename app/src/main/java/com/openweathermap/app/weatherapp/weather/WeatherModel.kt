package com.openweathermap.app.weatherapp.weather

import com.openweathermap.app.weatherapp.common.Weather
import io.reactivex.Single

interface WeatherModel {
    fun findByName(name: String): Single<Weather>
    fun findByZip(zip: String): Single<Weather>
    fun findByLocation(lat: Double, lon: Double): Single<Weather>
}