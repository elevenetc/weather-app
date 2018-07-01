package com.openweathermap.app.weatherapp.common.location

import io.reactivex.Single

interface LocationModel {
    /**
     * Returns current location or [com.openweathermap.app.weatherapp.common.exceptions.LocationNotAvailable]
     *
     * Client code must ask permission before calling this method
     */
    fun getCurrentLocation(): Single<Loc>
}