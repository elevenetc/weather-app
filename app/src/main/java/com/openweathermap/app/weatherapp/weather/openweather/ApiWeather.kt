package com.openweathermap.app.weatherapp.weather.openweather

data class ApiWeather(val main: Main, val name: String, val coord: Coord)

data class Main(val temp: Float)

data class Coord(val lon: Double, val lat: Double)