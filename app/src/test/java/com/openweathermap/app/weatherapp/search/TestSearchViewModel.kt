package com.openweathermap.app.weatherapp.search

import com.openweathermap.app.weatherapp.TestValues
import com.openweathermap.app.weatherapp.assertObservableSequence
import com.openweathermap.app.weatherapp.common.exceptions.LocationNotAvailable
import com.openweathermap.app.weatherapp.common.exceptions.NetworkException
import com.openweathermap.app.weatherapp.search.SearchState.Type.*
import com.openweathermap.app.weatherapp.utils.StubWeatherModel
import io.reactivex.Observable
import org.junit.Test


class TestSearchViewModel {

    private val weather = TestValues.WEATHER
    private val query = TestValues.QUERY

    @Test
    fun successfulQueries() {

        val weatherModel = StubWeatherModel.Builder().successValues().build()
        val queries = getWeatherQueries(weatherModel)

        for (query in queries)
            assertObservableSequence(
                    query,
                    SearchState(PROGRESS),
                    SearchState(RESULT, weather)
            )
    }

    @Test
    fun failedNetwork() {

        val weatherModel = StubWeatherModel.Builder().errorValues(NetworkException()).build()
        val queries = getWeatherQueries(weatherModel)

        for (query in queries)
            assertObservableSequence(
                    query,
                    SearchState(PROGRESS),
                    SearchState(NETWORK_ERROR)
            )
    }

    @Test
    fun unavailableLocation() {
        val model = StubWeatherModel.Builder().errorValues(LocationNotAvailable()).build()
        val weatherModel = SearchViewModel(model)
        val query = weatherModel.findWeatherAtCurrentLocation()

        assertObservableSequence(
                query,
                SearchState(PROGRESS),
                SearchState(NO_LOCATION_ERROR)
        )
    }

    private fun getWeatherQueries(weatherModel: StubWeatherModel): List<Observable<SearchState>> {
        return listOf(
                SearchViewModel(weatherModel).findWeatherByQueryId(query.id),
                SearchViewModel(weatherModel).findWeatherByCity(weather.name),
                SearchViewModel(weatherModel).findWeatherByZip(query.zipCode),
                SearchViewModel(weatherModel).findWeatherByQueryId(query.id)
        )
    }

}