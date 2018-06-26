package com.openweathermap.app.weatherapp.weather

import com.openweathermap.app.weatherapp.common.Weather
import com.openweathermap.app.weatherapp.location.LocationModel
import io.reactivex.Single
import javax.inject.Inject

class WeatherModelImpl @Inject constructor(
        private val weatherProvider: WeatherProvider,
        private val locationModel: LocationModel
) : WeatherModel {

    override fun findByName(name: String): Single<Weather> {
        return weatherProvider.findByName(name)
    }

    override fun findByZip(zip: String): Single<Weather> {
        return weatherProvider.findByZip(zip)
    }

    override fun findWeatherAtCurrentLocation(): Single<Weather> {
        return locationModel.getCurrentLocation().flatMap { loc ->
            weatherProvider.findByLocation(loc.latitude, loc.longitude)
        }
    }
}