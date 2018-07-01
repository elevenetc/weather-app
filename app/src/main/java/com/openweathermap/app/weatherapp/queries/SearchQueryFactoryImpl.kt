package com.openweathermap.app.weatherapp.queries

import javax.inject.Inject

class SearchQueryFactoryImpl @Inject constructor() : SearchQuery.Factory {
    override fun create(name: String, zip: String, lat: Double, lon: Double): SearchQuery {
        val result = SearchQuery()
        result.name = name
        result.zipCode = zip
        result.lat = lat
        result.lon = lon
        return result
    }
}