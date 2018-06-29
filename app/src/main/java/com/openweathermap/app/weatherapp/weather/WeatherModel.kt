package com.openweathermap.app.weatherapp.weather

import com.openweathermap.app.weatherapp.common.Weather
import com.openweathermap.app.weatherapp.queries.SearchQuery
import io.reactivex.Completable
import io.reactivex.Single

interface WeatherModel {
    /**
     * Search queries
     */
    fun getRecentQueries(): Single<List<SearchQuery>>

    fun getRecentQuery(): Single<SearchQuery>
    fun deleteQuery(searchQuery: SearchQuery): Completable
    fun deleteAllQueries(): Completable

    /**
     * Weather
     */
    fun findByQueryId(queryId: Int): Single<Weather>
    fun findByQuery(query: SearchQuery): Single<Weather>
    fun findByName(name: String): Single<Weather>
    fun findByZip(zip: String): Single<Weather>
    fun findWeatherAtCurrentLocation(): Single<Weather>


}