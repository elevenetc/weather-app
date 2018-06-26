package com.openweathermap.app.weatherapp.location

import android.location.Location
import io.reactivex.Single

interface LocationModel {
    fun getCurrentLocation(): Single<Location>
}