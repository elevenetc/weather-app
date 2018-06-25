package com.openweathermap.app.weatherapp.search

import com.openweathermap.app.weatherapp.common.Weather
import com.openweathermap.app.weatherapp.weather.WeatherModel
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val model: WeatherModel) {

    private var state: SearchState = SearchState(SearchState.Type.IDLE)

    fun findWeatherByCity(name: String): Observable<SearchState> {

        return Observable.concat(
                Observable.just(SearchState(SearchState.Type.PROGRESS)),
                model.findByName(name).map { result ->
                    SearchState(SearchState.Type.RESULT, Weather(result.name, result.temperature))
                }.toObservable().onErrorResumeNext(Observable.just(SearchState(SearchState.Type.NOT_FOUND)))
        ).doOnNext({ state ->
            this.state = state
        })
    }

    fun findWeatherByZip(zip: String): Observable<SearchState> {

        return Observable.concat(
                Observable.just(SearchState(SearchState.Type.PROGRESS)),
                model.findByZip(zip).map { result ->
                    SearchState(SearchState.Type.RESULT, Weather(result.name, result.temperature))
                }.toObservable().onErrorResumeNext(Observable.just(SearchState(SearchState.Type.NOT_FOUND)))
        ).doOnNext({ state ->
            this.state = state
        })
    }


    fun latestState(): Single<SearchState> {
        return Single.just(state)
    }
}