package com.openweathermap.app.weatherapp.search

import com.openweathermap.app.weatherapp.common.exceptions.LocationNotAvailable
import com.openweathermap.app.weatherapp.common.exceptions.NetworkException
import com.openweathermap.app.weatherapp.search.SearchState.Type
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
                just(SearchState(Type.PROGRESS)),
                model.getRecentQuery().flatMap {
                    model.findByQuery(it)
                }.map { weather -> SearchState(Type.RESULT, weather) }.toObservable()
                        .onErrorReturn { SearchState(Type.IDLE) }
        )
    }

    fun findWeatherAtCurrentLocation(): Observable<SearchState> {
        return concat(
                just(SearchState(Type.PROGRESS)),
                model.findWeatherAtCurrentLocation()
                        .map { result -> SearchState(Type.RESULT, result) }.toObservable()
                        .onErrorReturn { mapError(it) }
        )
    }

    fun findWeatherByCity(name: String): Observable<SearchState> {

        return concat(
                just(SearchState(Type.PROGRESS)),
                model.findByName(name)
                        .map { result -> SearchState(Type.RESULT, result) }.toObservable()
                        .onErrorReturn { mapError(it) }
        )
    }

    fun findWeatherByZip(zip: String): Observable<SearchState> {

        return concat(
                just(SearchState(Type.PROGRESS)),
                model.findByZip(zip)
                        .map { result -> SearchState(Type.RESULT, result) }.toObservable()
                        .onErrorReturn { mapError(it) }
        )
    }

    private fun mapError(t: Throwable): SearchState {
        return when (t) {
            is NetworkException -> SearchState(Type.NETWORK_ERROR)
            is LocationNotAvailable -> SearchState(Type.NO_LOCATION_ERROR)
            else -> SearchState(Type.NOT_FOUND)
        }
    }

}