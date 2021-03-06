package com.openweathermap.app.weatherapp.weather.openweather

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {
    @GET("weather")
    fun searchCity(@Query("q") city: String): Single<ApiWeather>

    @GET("weather")
    fun searchZipCode(@Query("zip") zip: String): Single<ApiWeather>

    @GET("weather")
    fun searchByLocation(@Query("lat") lat: Double, @Query("lon") lon: Double, @Query("cnt") count: Int = 1): Single<ApiWeather>

    @GET("weather")
    fun search(
            @Query("q") city: String,
            @Query("zip") zip: String,
            @Query("lat") lat: Double,
            @Query("lon") lon: Double,
            @Query("cnt") count: Int = 1): Single<ApiWeather>
}