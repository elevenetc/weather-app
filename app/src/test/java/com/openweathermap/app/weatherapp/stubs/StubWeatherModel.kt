package com.openweathermap.app.weatherapp.stubs

import com.openweathermap.app.weatherapp.common.Weather
import com.openweathermap.app.weatherapp.queries.SearchQuery
import com.openweathermap.app.weatherapp.weather.WeatherModel
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

class StubWeatherModel : WeatherModel {

    private val weather: Single<Weather>

    override fun getRecentQueries(): Single<List<SearchQuery>> {
        return Single.error(NotImplementedError())
    }

    override fun deleteAllQueries(): Completable {
        return Completable.error(NotImplementedError())
    }


    override fun deleteQuery(searchQuery: SearchQuery): Completable {
        return Completable.error(NotImplementedError())
    }

    constructor(weather: Weather) {
        this.weather = Single.just(weather)
    }

    constructor(weather: Single<Weather>) {
        this.weather = weather
    }

    override fun findByName(name: String): Single<Weather> {
        return weather
    }

    override fun findWeatherAtCurrentLocation(): Single<Weather> {
        return weather
    }

    override fun getRecentQuery(): Maybe<SearchQuery> {
        return Maybe.error(NotImplementedError())
    }

    override fun findByZip(zip: String): Single<Weather> {
        return weather
    }
}