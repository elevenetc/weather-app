package com.openweathermap.app.weatherapp.weather

import com.openweathermap.app.weatherapp.common.Weather
import io.reactivex.Single
import javax.inject.Inject

class WeatherModelImpl @Inject constructor(private val weatherProvider: WeatherProvider) : WeatherModel {

    override fun findByName(name: String): Single<Weather> {
        return weatherProvider.findByName(name)
    }

    override fun findByZip(zip: String): Single<Weather> {
        return weatherProvider.findByZip(zip)
    }

    override fun findByLocation(lat: Double, lon: Double): Single<Weather> {
        return weatherProvider.findByLocation(lat, lon)
    }
}