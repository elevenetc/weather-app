package com.openweathermap.app.weatherapp.utils

import com.openweathermap.app.weatherapp.TestValues
import com.openweathermap.app.weatherapp.common.Weather
import com.openweathermap.app.weatherapp.queries.NoRecentQueryException
import com.openweathermap.app.weatherapp.queries.SearchQuery
import com.openweathermap.app.weatherapp.queries.WeatherNotFound
import com.openweathermap.app.weatherapp.weather.WeatherModel
import io.reactivex.Completable
import io.reactivex.Single

class StubWeatherModel : WeatherModel {

    private val weather: Single<Weather>
    private val queries: Single<List<SearchQuery>>
    private val recentQuery: Single<SearchQuery>

    private constructor(weather: Weather,
                        query: List<SearchQuery>,
                        recentQuery: SearchQuery
    ) {
        this.weather = Single.just(weather)
        this.queries = Single.just(query)
        this.recentQuery = Single.just(recentQuery)
    }

    private constructor(weather: Single<Weather> = Single.error(WeatherNotFound()),
                        query: Single<List<SearchQuery>> = Single.just(emptyList()),
                        recentQuery: Single<SearchQuery> = Single.error(NoRecentQueryException())) {
        this.weather = weather
        this.queries = query
        this.recentQuery = recentQuery
    }

    override fun findByQuery(query: SearchQuery): Single<Weather> {
        return weather
    }

    override fun getAllQueries(): Single<List<SearchQuery>> {
        return Single.just(queries.blockingGet())
    }

    override fun deleteAllQueries(): Completable {
        return Completable.complete()
    }

    override fun deleteQuery(searchQuery: SearchQuery): Completable {
        return Completable.complete()
    }

    override fun findByName(name: String): Single<Weather> {
        return weather
    }

    override fun findWeatherAtCurrentLocation(): Single<Weather> {
        return weather
    }

    override fun findByQueryId(queryId: Int): Single<Weather> {
        return weather
    }

    override fun getRecentQuery(): Single<SearchQuery> {
        return recentQuery
    }

    override fun findByZip(zip: String): Single<Weather> {
        return weather
    }

    class Builder {

        private lateinit var weather: Single<Weather>
        private lateinit var queries: Single<List<SearchQuery>>
        private lateinit var recentQuery: Single<SearchQuery>

        fun errorValues(
                exception: Throwable
        ): Builder {
            this.weather = Single.error(exception)
            this.queries = Single.error(exception)
            this.recentQuery = Single.error(exception)
            return this
        }

        fun successValues(): Builder {
            weather = Single.just(TestValues.WEATHER)
            queries = Single.just(listOf(TestValues.QUERY))
            recentQuery = Single.just(TestValues.QUERY)
            return this
        }

        fun build(): StubWeatherModel {
            return StubWeatherModel(weather, queries, recentQuery)
        }
    }
}