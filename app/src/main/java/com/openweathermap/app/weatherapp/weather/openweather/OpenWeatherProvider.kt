package com.openweathermap.app.weatherapp.weather.openweather

import com.openweathermap.app.weatherapp.common.Weather
import com.openweathermap.app.weatherapp.exceptions.*
import com.openweathermap.app.weatherapp.logs.Logger
import com.openweathermap.app.weatherapp.weather.WeatherProvider
import io.reactivex.Single
import retrofit2.HttpException
import javax.inject.Inject


class OpenWeatherProvider @Inject constructor(private val api: OpenWeatherApi,
                                              private val logger: Logger) : WeatherProvider {

    override fun findByName(name: String): Single<Weather> {
        return api.searchCity(name).map {
            Weather(it.name, it.main.temp)
        }.onErrorResumeNext { error ->
            handleCallError(error)
        }.doOnError {
            logger.log(it)
        }
    }

    override fun findByLocation(lat: Double, lon: Double): Single<Weather> {
        return Single.error(NotImplementedException())
    }

    override fun findByZip(code: String): Single<Weather> {
        return api.searchZipCode(code).map {
            Weather(it.name, it.main.temp)
        }.onErrorResumeNext { error ->
            handleCallError(error)
        }.doOnError {
            logger.log(it)
        }
    }

    private fun handleCallError(t: Throwable): Single<Weather> {
        return if (t is HttpException)
            when {
                t.code() == 404 -> Single.error(NotFoundWeather(t))
                t.code() >= 500 -> Single.error(BackendIssuesException(t))
                else -> Single.error(SearchWeatherException(t))
            }
        else Single.error(NetworkErrorException(t))
    }

}