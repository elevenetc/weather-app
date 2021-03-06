package com.openweathermap.app.weatherapp.utils

import com.openweathermap.app.weatherapp.common.Weather
import com.openweathermap.app.weatherapp.queries.SearchQuery
import com.openweathermap.app.weatherapp.weather.WeatherProvider
import io.reactivex.Single

class StubWeatherProvider : WeatherProvider {

    private val weather: Single<Weather>

    constructor(weather: Weather) {
        this.weather = Single.just(weather)
    }

    constructor(weather: Single<Weather>) {
        this.weather = weather
    }

    override fun findByQuery(query: SearchQuery): Single<Weather> {
        return weather
    }

    override fun findByName(name: String): Single<Weather> {
        return weather
    }

    override fun findByLocation(lat: Double, lon: Double): Single<Weather> {
        return weather
    }

    override fun findByZip(code: String): Single<Weather> {
        return weather
    }
}