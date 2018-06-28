package com.openweathermap.app.weatherapp.queries

import io.reactivex.Observable

interface QueriesViewModel {
    fun getQueries(): Observable<QueriesState>
    fun deleteAllQueries(): Observable<QueriesState>
    fun deleteQuery(query: SearchQuery): Observable<QueriesState>
}