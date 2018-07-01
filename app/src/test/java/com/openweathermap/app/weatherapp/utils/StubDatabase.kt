package com.openweathermap.app.weatherapp.utils

import com.openweathermap.app.weatherapp.data.Database
import com.openweathermap.app.weatherapp.queries.SearchQueryDao


class StubDatabase(private val searchQueryDao: SearchQueryDao) : Database {

    override fun searchQueries(): SearchQueryDao {
        return searchQueryDao
    }

}