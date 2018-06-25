package com.openweathermap.app.weatherapp.weather.openweather

import okhttp3.Interceptor
import okhttp3.Response

internal class AuthInterceptor(private val key: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url()

        val url = originalHttpUrl.newBuilder()
                .addQueryParameter("APPID", key)
                .build()

        return chain.proceed(original.newBuilder().url(url).build())
    }

}