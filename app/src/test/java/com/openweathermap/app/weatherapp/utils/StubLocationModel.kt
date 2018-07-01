package com.openweathermap.app.weatherapp.utils

import com.openweathermap.app.weatherapp.common.exceptions.LocationNotAvailable
import com.openweathermap.app.weatherapp.common.location.Loc
import com.openweathermap.app.weatherapp.common.location.LocationModel
import io.reactivex.Single

class StubLocationModel(private val location: Loc? = null) : LocationModel {
    override fun getCurrentLocation(): Single<Loc> {
        return if (location == null) {
            Single.error(LocationNotAvailable())
        } else {
            Single.just(location)
        }
    }
}