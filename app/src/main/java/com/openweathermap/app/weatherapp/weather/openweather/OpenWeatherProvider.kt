package com.openweathermap.app.weatherapp.weather.openweather

import com.openweathermap.app.weatherapp.common.Weather
import com.openweathermap.app.weatherapp.common.exceptions.BackendIssuesException
import com.openweathermap.app.weatherapp.common.exceptions.NetworkException
import com.openweathermap.app.weatherapp.common.exceptions.NotFoundWeather
import com.openweathermap.app.weatherapp.common.exceptions.SearchWeatherException
import com.openweathermap.app.weatherapp.common.logs.Logger
import com.openweathermap.app.weatherapp.weather.WeatherProvider
import io.reactivex.Single
import io.reactivex.SingleTransformer
import retrofit2.HttpException
import javax.inject.Inject


class OpenWeatherProvider @Inject constructor(private val api: OpenWeatherApi,
                                              private val logger: Logger,
                                              private val mapper: OpenWeatherMapper) : WeatherProvider {

    override fun findByName(name: String): Single<Weather> {
        return api.searchCity(name).map {
            mapper.toWeather(it)
        }.compose(handleError())
    }

    override fun findByLocation(lat: Double, lon: Double): Single<Weather> {
        return api.searchByLocation(lat, lon).map {
            mapper.toWeather(it)
        }.compose(handleError())
    }

    override fun findByZip(code: String): Single<Weather> {
        return api.searchZipCode(code).map {
            mapper.toWeather(it)
        }.compose(handleError())
    }

    private fun handleError(): SingleTransformer<Weather, Weather> {
        return SingleTransformer({ upstream ->
            upstream.onErrorResumeNext { it ->
                mapError(it)
            }.doOnError {
                logger.log(it)
            }
        })
    }

}


private fun mapError(t: Throwable): Single<Weather> {
    return if (t is HttpException)
        when {
            t.code() == 404 -> Single.error(NotFoundWeather(t))
            t.code() >= 500 -> Single.error(BackendIssuesException(t))
            else -> Single.error(SearchWeatherException(t))
        }
    else Single.error(NetworkException(t))
}