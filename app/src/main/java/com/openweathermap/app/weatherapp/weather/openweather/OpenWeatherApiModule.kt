package com.openweathermap.app.weatherapp.weather.openweather

import com.openweathermap.app.weatherapp.BuildConfig
import com.openweathermap.app.weatherapp.common.Names
import com.openweathermap.app.weatherapp.weather.WeatherModel
import com.openweathermap.app.weatherapp.weather.WeatherModelImpl
import com.openweathermap.app.weatherapp.weather.WeatherProvider
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class OpenWeatherApiModule {

    @Provides
    fun weatherModel(inst: WeatherModelImpl): WeatherModel = inst

    @Provides
    fun weatherProvider(inst: OpenWeatherProvider): WeatherProvider = inst

    @Provides
    @Named(Names.WEATHER_API_KEY)
    fun apiKey(): String {
        return BuildConfig.OPEN_WEATHER_API_KEY
    }

    @Provides
    @Named(Names.WEATHER_API_ENDPOINT)
    fun endpoint(): String {
        return "https://api.openweathermap.org/data/2.5/"
    }

    @Singleton
    @Provides
    fun openWeatherApi(@Named(Names.WEATHER_API_ENDPOINT) endpoint: String,
                       @Named(Names.WEATHER_API_KEY) key: String): OpenWeatherApi {
        val client = OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor(key))
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl(endpoint)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        return retrofit.create<OpenWeatherApi>(OpenWeatherApi::class.java)
    }
}