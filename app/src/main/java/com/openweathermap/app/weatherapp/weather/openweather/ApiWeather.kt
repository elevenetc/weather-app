package com.openweathermap.app.weatherapp.weather.openweather

data class ApiWeather(val main: Main, val name:String)

data class Main(val temp: Float)