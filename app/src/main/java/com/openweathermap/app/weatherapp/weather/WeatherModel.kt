package com.openweathermap.app.weatherapp.weather

import com.openweathermap.app.weatherapp.common.Weather
import com.openweathermap.app.weatherapp.queries.SearchQuery
import io.reactivex.Completable
import io.reactivex.Single

interface WeatherModel {
    fun findByName(name: String): Single<Weather>
    fun findByZip(zip: String): Single<Weather>
    fun findWeatherAtCurrentLocation(): Single<Weather>
    fun getRecentQueries(): Single<List<SearchQuery>>
    fun deleteAllQueries(): Completable
}