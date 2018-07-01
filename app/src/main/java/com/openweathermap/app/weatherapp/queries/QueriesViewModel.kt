package com.openweathermap.app.weatherapp.queries

import io.reactivex.Observable

interface QueriesViewModel {
    /**
     * Returns all previously made successful weather search queries
     * Two possible results:
     * - [com.openweathermap.app.weatherapp.queries.QueriesState.Type.RESULT]
     * - [com.openweathermap.app.weatherapp.queries.QueriesState.Type.EMPTY]
     */
    fun getAllQueries(): Observable<QueriesState>

    /**
     * Removes all queries
     * Result:
     * - [com.openweathermap.app.weatherapp.queries.QueriesState.Type.ALL_DELETED]
     */
    fun deleteAllQueries(): Observable<QueriesState>

    /**
     * Removes query and returns results of [getAllQueries]
     */
    fun deleteQuery(query: SearchQuery): Observable<QueriesState>
}