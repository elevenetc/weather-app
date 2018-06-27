package com.openweathermap.app.weatherapp.common.location

import android.location.Location
import io.reactivex.Single

interface LocationModel {
    fun getCurrentLocation(): Single<Location>
}