package com.openweathermap.app.weatherapp.search

import io.reactivex.Observable

interface SearchViewModel {
    fun findWeatherByRecentSearch(): Observable<SearchState>
    fun findWeatherByCity(name: String): Observable<SearchState>
    fun findWeatherByZip(zip: String): Observable<SearchState>
    fun findWeatherByQueryId(id: Int): Observable<SearchState>
    fun findWeatherAtCurrentLocation(): Observable<SearchState>
}