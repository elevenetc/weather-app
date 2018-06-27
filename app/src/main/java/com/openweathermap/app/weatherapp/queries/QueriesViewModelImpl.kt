package com.openweathermap.app.weatherapp.queries

import com.openweathermap.app.weatherapp.queries.QueriesState.Type.*
import com.openweathermap.app.weatherapp.weather.WeatherModel
import io.reactivex.Observable
import io.reactivex.Observable.concat
import io.reactivex.Observable.just
import javax.inject.Inject

class QueriesViewModelImpl @Inject constructor(private val model: WeatherModel) : QueriesViewModel {
    override fun getQueries(): Observable<QueriesState> {
        return concat(
                just(QueriesState(PROGRESS)),
                model.getRecentQueries().map { queries ->
                    if (queries.isEmpty()) {
                        QueriesState(EMPTY)
                    } else {
                        QueriesState(RESULT, queries)
                    }
                }.toObservable()
        )
    }

    override fun deleteAllQueries(): Observable<QueriesState> {
        return concat(
                just(QueriesState(DELETING_QUERIES)),
                model.deleteAllQueries().toSingle { ALL_DELETED }.toObservable()
        )
    }
}