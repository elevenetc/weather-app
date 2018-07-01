package com.openweathermap.app.weatherapp.common.exceptions

class NetworkException : RuntimeException {
    constructor(cause: Throwable) : super(cause)
    constructor() : super()
}