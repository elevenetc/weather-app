package com.openweathermap.app.weatherapp.queries

import com.openweathermap.app.weatherapp.TestValues
import com.openweathermap.app.weatherapp.assertObservableSequence
import com.openweathermap.app.weatherapp.queries.QueriesState.Type.*
import com.openweathermap.app.weatherapp.utils.StubWeatherModel
import com.openweathermap.app.weatherapp.weather.WeatherModel
import io.reactivex.Completable.complete
import io.reactivex.Single
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class TestQueriesViewModel {
    @Test
    fun nonEmptyResult() {
        val model = StubWeatherModel.Builder().successValues().build()
        val viewModel = QueriesViewModelImpl(model)

        assertObservableSequence(
                viewModel.getAllQueries(),
                QueriesState(PROGRESS),
                QueriesState(RESULT, listOf(TestValues.QUERY))
        )
    }

    @Test
    fun emptyResult() {
        val model = StubWeatherModel.Builder().successValues().build()
        val viewModel = QueriesViewModelImpl(model)

        assertObservableSequence(
                viewModel.getAllQueries(),
                QueriesState(PROGRESS),
                QueriesState(RESULT, listOf(TestValues.QUERY))
        )
    }

    @Test
    fun deletingAllQueries() {
        val model = mock(WeatherModel::class.java)
        val viewModel = QueriesViewModelImpl(model)
        Mockito.`when`(model.deleteAllQueries()).thenReturn(complete())

        assertObservableSequence(
                viewModel.deleteAllQueries(),
                QueriesState(DELETING_QUERIES),
                QueriesState(ALL_DELETED)
        )
        verify(model).deleteAllQueries()
    }

    @Test
    fun deletingQuery() {
        val model = mock(WeatherModel::class.java)
        val viewModel = QueriesViewModelImpl(model)
        val query = TestValues.QUERY
        Mockito.`when`(model.deleteQuery(query)).thenReturn(complete())
        Mockito.`when`(model.getAllQueries()).thenReturn(Single.just(emptyList()))

        assertObservableSequence(
                viewModel.deleteQuery(query),
                QueriesState(DELETING_QUERIES),
                QueriesState(PROGRESS),
                QueriesState(EMPTY)
        )
        verify(model).deleteQuery(query)
    }
}