package com.openweathermap.app.weatherapp.data

import com.openweathermap.app.weatherapp.queries.SearchQueryDao

interface Database {
    fun searchQueries(): SearchQueryDao
}