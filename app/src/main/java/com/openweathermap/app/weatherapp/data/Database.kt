package com.openweathermap.app.weatherapp.data

import com.openweathermap.app.weatherapp.recent.SearchQueryDao

interface Database {
    fun searchQueries(): SearchQueryDao
}