package com.openweathermap.app.weatherapp.weather

import com.openweathermap.app.weatherapp.common.Weather
import com.openweathermap.app.weatherapp.queries.SearchQuery
import io.reactivex.Completable
import io.reactivex.Single

interface WeatherModel {

    fun deleteQuery(searchQuery: SearchQuery): Completable
    fun deleteAllQueries(): Completable

    /**
     * Returns successful search queries made in the past
     */
    fun getAllQueries(): Single<List<SearchQuery>>

    /**
     * Returns the most recent search query
     * If there is no such returns [com.openweathermap.app.weatherapp.queries.NoRecentQueryException]
     */
    fun getRecentQuery(): Single<SearchQuery>

    /**
     * Makes search by [com.openweathermap.app.weatherapp.queries.SearchQuery.id] field
     *
     * If not found returns errors:
     * - [com.openweathermap.app.weatherapp.queries.WeatherNotFound]
     * - [com.openweathermap.app.weatherapp.common.exceptions.NetworkException]
     */
    fun findByQueryId(queryId: Int): Single<Weather>

    /**
     * Makes search by [com.openweathermap.app.weatherapp.queries.SearchQuery]
     * And stores search query into database
     *
     * If not found returns errors:
     * - [com.openweathermap.app.weatherapp.queries.WeatherNotFound]
     * - [com.openweathermap.app.weatherapp.common.exceptions.NetworkException]
     */
    fun findByQuery(query: SearchQuery): Single<Weather>

    /**
     * Makes search by location name
     * And stores search query into database
     *
     * If not found returns errors:
     * - [com.openweathermap.app.weatherapp.queries.WeatherNotFound]
     * - [com.openweathermap.app.weatherapp.common.exceptions.NetworkException]
     */
    fun findByName(name: String): Single<Weather>

    /**
     * Makes search by location address
     * And stores search query into database
     *
     * If not found returns errors:
     * - [com.openweathermap.app.weatherapp.queries.WeatherNotFound]
     * - [com.openweathermap.app.weatherapp.common.exceptions.NetworkException]
     */
    fun findByZip(zip: String): Single<Weather>

    /**
     * Makes search by current location
     * And stores search query into database
     *
     * If not found returns errors:
     * - [com.openweathermap.app.weatherapp.queries.WeatherNotFound]
     * - [com.openweathermap.app.weatherapp.common.exceptions.NetworkException]
     * - [com.openweathermap.app.weatherapp.common.exceptions.LocationNotAvailable]
     */
    fun findWeatherAtCurrentLocation(): Single<Weather>


}