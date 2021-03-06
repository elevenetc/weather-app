package com.openweathermap.app.weatherapp.weather

import com.openweathermap.app.weatherapp.common.Weather
import com.openweathermap.app.weatherapp.queries.SearchQuery
import io.reactivex.Single

interface WeatherProvider {
    fun findByName(name: String): Single<Weather>
    fun findByLocation(lat: Double, lon: Double): Single<Weather>
    fun findByZip(code: String): Single<Weather>
    fun findByQuery(query: SearchQuery): Single<Weather>
}