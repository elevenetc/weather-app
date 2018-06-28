package com.openweathermap.app.weatherapp.weather

import com.openweathermap.app.weatherapp.common.Weather
import com.openweathermap.app.weatherapp.common.location.LocationModel
import com.openweathermap.app.weatherapp.data.Database
import com.openweathermap.app.weatherapp.queries.SearchQuery
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class WeatherModelImpl @Inject constructor(
        private val weatherProvider: WeatherProvider,
        private val locationModel: LocationModel,
        private val database: Database
) : WeatherModel {

    override fun findByName(name: String): Single<Weather> {
        return weatherProvider.findByName(name).doOnSuccess {
            database.searchQueries().insert(SearchQuery.create(name))
        }
    }

    override fun findByZip(zip: String): Single<Weather> {
        return weatherProvider.findByZip(zip).doOnSuccess {
            database.searchQueries().insert(SearchQuery.create(zip = zip))
        }
    }

    override fun findWeatherAtCurrentLocation(): Single<Weather> {
        return locationModel.getCurrentLocation().flatMap { loc ->
            weatherProvider.findByLocation(loc.latitude, loc.longitude)
        }.doOnSuccess { weather ->
            database.searchQueries().insert(SearchQuery.create(lat = weather.lat, lon = weather.lon))
        }
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

    override fun getRecentQueries(): Single<List<SearchQuery>> {
        return database.searchQueries().all
    }
}