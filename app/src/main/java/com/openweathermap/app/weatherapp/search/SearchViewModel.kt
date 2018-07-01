package com.openweathermap.app.weatherapp.search

import com.openweathermap.app.weatherapp.common.exceptions.LocationNotAvailable
import com.openweathermap.app.weatherapp.common.exceptions.NetworkException
import com.openweathermap.app.weatherapp.search.SearchState.Type.*
import com.openweathermap.app.weatherapp.weather.WeatherModel
import io.reactivex.Observable
import io.reactivex.Observable.concat
import io.reactivex.Observable.just
import javax.inject.Inject


class SearchViewModel @Inject constructor(
        private val model: WeatherModel
) {

    fun findWeatherByRecentSearch(): Observable<SearchState> {
        return concat(
                just(SearchState(PROGRESS)),
                model.getRecentQuery()
                        .flatMap { model.findByQuery(it) }
                        .map { weather -> SearchState(RESULT, weather) }.toObservable()
                        .onErrorReturn { SearchState(IDLE) }
        )
    }

    fun findWeatherByCity(name: String): Observable<SearchState> {

        return concat(
                just(SearchState(PROGRESS)),
                model.findByName(name)
                        .map { result -> SearchState(RESULT, result) }.toObservable()
                        .onErrorReturn { mapError(it) }
        )
    }

    fun findWeatherByZip(zip: String): Observable<SearchState> {

        return concat(
                just(SearchState(PROGRESS)),
                model.findByZip(zip)
                        .map { result -> SearchState(RESULT, result) }.toObservable()
                        .onErrorReturn { mapError(it) }
        )
    }

    fun findWeatherByQueryId(id: Int): Observable<SearchState> {

        return concat(
                just(SearchState(PROGRESS)),
                model.findByQueryId(id)
                        .map { result -> SearchState(RESULT, result) }.toObservable()
                        .onErrorReturn { mapError(it) }
        )
    }

    fun findWeatherAtCurrentLocation(): Observable<SearchState> {
        return concat(
                just(SearchState(PROGRESS)),
                model.findWeatherAtCurrentLocation()
                        .map { result -> SearchState(RESULT, result) }.toObservable()
                        .onErrorReturn { mapError(it) }
        )
    }

    private fun mapError(t: Throwable): SearchState {
        return when (t) {
            is NetworkException -> SearchState(NETWORK_ERROR)
            is LocationNotAvailable -> SearchState(NO_LOCATION_ERROR)
            else -> SearchState(NOT_FOUND)
        }
    }

}