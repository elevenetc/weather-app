package com.openweathermap.app.weatherapp.weather

import com.openweathermap.app.weatherapp.common.Weather
import com.openweathermap.app.weatherapp.common.location.LocationModel
import com.openweathermap.app.weatherapp.data.Database
import com.openweathermap.app.weatherapp.queries.NoRecentQueryException
import com.openweathermap.app.weatherapp.queries.SearchQuery
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class WeatherModelImpl @Inject constructor(
        private val weatherProvider: WeatherProvider,
        private val locationModel: LocationModel,
        private val database: Database,
        private val searchQueryFactory: SearchQuery.Factory
) : WeatherModel {

    override fun findByQuery(query: SearchQuery): Single<Weather> {
        return weatherProvider.findByQuery(query)
    }

    override fun findByName(name: String): Single<Weather> {
        return weatherProvider.findByName(name).doOnSuccess {
            database.searchQueries().insert(searchQueryFactory.create(name))
        }
    }

    override fun findByZip(zip: String): Single<Weather> {
        return weatherProvider.findByZip(zip).doOnSuccess {
            database.searchQueries().insert(searchQueryFactory.create(zip = zip))
        }
    }

    override fun findWeatherAtCurrentLocation(): Single<Weather> {
        return locationModel.getCurrentLocation().flatMap { loc ->
            weatherProvider.findByLocation(loc.lat, loc.lon)
        }.doOnSuccess { weather ->
            database.searchQueries().insert(searchQueryFactory.create(lat = weather.lat, lon = weather.lon))
        }
    }

    override fun getAllQueries(): Single<List<SearchQuery>> {
        return database.searchQueries().getAll()
    }

    override fun findByQueryId(queryId: Int): Single<Weather> {
        return database.searchQueries().getById(queryId).flatMap {
            findByQuery(it)
        }
    }

    override fun getRecentQuery(): Single<SearchQuery> {
        return database.searchQueries()
                .getLatest()
                .onErrorResumeNext(Single.error(NoRecentQueryException()))
    }

    override fun deleteAllQueries(): Completable {
        return Completable.fromCallable {
            database.searchQueries().deleteAll()
        }
    }

    override fun deleteQuery(searchQuery: SearchQuery): Completable {
        return Completable.fromCallable {
            database.searchQueries().delete(searchQuery)
        }
    }
}