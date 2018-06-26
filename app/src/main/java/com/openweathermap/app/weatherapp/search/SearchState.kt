package com.openweathermap.app.weatherapp.search

import com.openweathermap.app.weatherapp.common.Weather

private val EMPTY_WEATHER = Weather("empty-weather", 0f)

class SearchState(val type: Type, val weather: Weather = EMPTY_WEATHER) {

    enum class Type {
        IDLE, PROGRESS, RESULT, NOT_FOUND, NETWORK_ERROR, NO_LOCATION_ERROR
    }

    init {
        validateState()
    }

    private fun validateState() {
        if ((type != Type.RESULT && weather != EMPTY_WEATHER) || type == Type.RESULT && weather == EMPTY_WEATHER) {
            throw IllegalArgumentException(
                    "Only ${Type.RESULT} can have weather result. Type: $type, Weather: $weather"
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other !is SearchState) return false
        return other.type == type && other.weather == weather
    }

    override fun hashCode(): Int {
        var result = type.hashCode()
        result = 31 * result + weather.hashCode()
        return result
    }
}

