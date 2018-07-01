package com.openweathermap.app.weatherapp.weather

import com.openweathermap.app.weatherapp.TestValues
import com.openweathermap.app.weatherapp.assertSingle
import com.openweathermap.app.weatherapp.assertSingleError
import com.openweathermap.app.weatherapp.queries.NoRecentQueryException
import com.openweathermap.app.weatherapp.queries.SearchQuery
import com.openweathermap.app.weatherapp.queries.SearchQuery.Factory
import com.openweathermap.app.weatherapp.queries.SearchQueryDao
import com.openweathermap.app.weatherapp.utils.StubDatabase
import com.openweathermap.app.weatherapp.utils.StubLocationModel
import com.openweathermap.app.weatherapp.utils.StubWeatherProvider
import io.reactivex.Single.error
import io.reactivex.Single.just
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify

class TestWeatherModel {

    private val name = TestValues.LOC_NAME
    private val query = TestValues.QUERY
    private val weather = TestValues.WEATHER
    private val zip = TestValues.ZIP
    private val loc = TestValues.LOC

    private lateinit var queryDao: SearchQueryDao
    private lateinit var model: WeatherModel

    @Before
    fun setUp() {
        queryDao = Mockito.mock(SearchQueryDao::class.java)
        model = WeatherModelImpl(
                StubWeatherProvider(weather),
                StubLocationModel(loc),
                StubDatabase(queryDao),
                queryFactory(query)
        )
    }

    @Test
    fun successNameSearchAndStore() {
        assertSingle(model.findByName(name), weather)
        verify(queryDao).insert(query)
    }

    @Test
    fun successZipSearchAndStore() {
        assertSingle(model.findByZip(zip), weather)
        verify(queryDao).insert(query)
    }

    @Test
    fun successLocationSearchAndStore() {
        assertSingle(model.findWeatherAtCurrentLocation(), weather)
        verify(queryDao).insert(query)
    }

    @Test
    fun successSearchByQuery() {
        assertSingle(model.findByQuery(query), weather)
    }

    @Test
    fun successGetAllQueries() {
        Mockito.`when`(queryDao.getAll()).thenReturn(just(listOf(query)))
        assertSingle(model.getAllQueries(), listOf(query))
    }

    @Test
    fun successQueryById() {
        Mockito.`when`(queryDao.getById(query.id)).thenReturn(just(query))
        assertSingle(model.findByQueryId(query.id), weather)
    }

    @Test
    fun successRecentQuery() {
        Mockito.`when`(queryDao.getLatest()).thenReturn(just(query))
        assertSingle(model.getRecentQuery(), query)
    }

    @Test
    fun noRecentQuery() {
        Mockito.`when`(queryDao.getLatest()).thenReturn(error(NoRecentQueryException()))
        assertSingleError(model.getRecentQuery(), NoRecentQueryException::class.java)
    }

    @Test
    fun deleteQuery() {
        model.deleteQuery(query).test()
        verify(queryDao).delete(query)
    }

    @Test
    fun deleteAllQueries() {
        model.deleteAllQueries().test()
        verify(queryDao).deleteAll()
    }

    private fun queryFactory(query: SearchQuery): Factory {
        return object : Factory {
            override fun create(name: String, zip: String, lat: Double, lon: Double): SearchQuery {
                return query
            }
        }
    }
}