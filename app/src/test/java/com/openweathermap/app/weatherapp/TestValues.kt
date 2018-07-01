package com.openweathermap.app.weatherapp

import com.openweathermap.app.weatherapp.common.Weather
import com.openweathermap.app.weatherapp.common.location.Loc
import com.openweathermap.app.weatherapp.queries.SearchQuery
import java.util.*

object TestValues {

    const val TEMPERATURE = 10f
    const val LOC_NAME = "London"
    const val LAT = 10.0
    const val LON = 10.0
    val LOC = Loc(LAT, LON)
    const val ZIP = "3000"
    const val QUERY_ID = 10
    const val CRETED_AT_TIME = 3000L
    val CRETED_AT_DATE = Date(CRETED_AT_TIME)

    val WEATHER = Weather(LOC_NAME, TEMPERATURE)
    val QUERY: SearchQuery

    init {
        val query = SearchQuery()
        query.id = QUERY_ID
        query.lat = LAT
        query.lon = LON
        query.createdAt = CRETED_AT_DATE
        query.zipCode = ZIP
        query.name = LOC_NAME

        QUERY = query
    }
}