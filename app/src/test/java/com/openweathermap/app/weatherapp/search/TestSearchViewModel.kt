package com.openweathermap.app.weatherapp.search

import com.openweathermap.app.weatherapp.Values
import com.openweathermap.app.weatherapp.stubs.StubWeatherModel
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Test


class TestSearchViewModel {
    @Test
    fun testSuccessfulNameSearch() {

        val weather = Values.WEATHER
        val vm = SearchViewModel(StubWeatherModel(weather))
        val observer = TestObserver<SearchState>()

        vm.findWeatherByCity(weather.name).subscribe(observer)

        observer.assertNoErrors()
        observer.assertComplete()
        observer.assertValueSequence(listOf(
                SearchState(SearchState.Type.PROGRESS),
                SearchState(SearchState.Type.RESULT, weather)
        ))
    }

    @Test
    fun testFailedNameSearch() {

        val weather = Values.WEATHER
        val vm = SearchViewModel(StubWeatherModel(Single.error(RuntimeException())))
        val observer = TestObserver<SearchState>()

        vm.findWeatherByCity(weather.name).subscribe(observer)

        observer.assertNoErrors()
        observer.assertComplete()
        observer.assertValueSequence(listOf(
                SearchState(SearchState.Type.PROGRESS),
                SearchState(SearchState.Type.NOT_FOUND)
        ))
    }
}