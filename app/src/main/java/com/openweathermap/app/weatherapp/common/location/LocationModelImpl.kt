package com.openweathermap.app.weatherapp.common.location

import android.content.Context
import android.location.Location
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnSuccessListener
import com.openweathermap.app.weatherapp.common.exceptions.LocationNotAvailable
import com.openweathermap.app.weatherapp.common.scheduling.CurrentThreadExecutor
import io.reactivex.Observer
import io.reactivex.Single
import javax.inject.Inject

class LocationModelImpl @Inject constructor(private val context: Context) : LocationModel {


    @SuppressWarnings("MissingPermission")
    override fun getCurrentLocation(): Single<Loc> {

        val locProvider = LocationServices.getFusedLocationProviderClient(context)

        return Single.fromObservable<Loc> { observer ->

            locProvider.lastLocation.addOnSuccessListener(CurrentThreadExecutor(), OnSuccessListener<Location> { loc ->

                //TODO: add check if location staled or not accurate

                if (loc == null) {
                    requestUpdate(locProvider, observer)
                } else {
                    observer.onNext(Loc(loc.latitude, loc.longitude))
                    observer.onComplete()
                }
            })
        }
    }

    @SuppressWarnings("MissingPermission")
    private fun requestUpdate(locProvider: FusedLocationProviderClient, observer: Observer<in Loc>) {
        val request = LocationRequest()
        request.interval = 1000
        request.numUpdates = 1
        request.maxWaitTime = 1000
        locProvider.requestLocationUpdates(request, object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {

                locProvider.removeLocationUpdates(this)

                val loc = result.lastLocation

                if (loc == null) {
                    completeWithError()
                } else {
                    observer.onNext(Loc(loc.latitude, loc.longitude))
                    observer.onComplete()
                }
            }

            override fun onLocationAvailability(availability: LocationAvailability) {
                if (!availability.isLocationAvailable) completeWithError()
            }

            private fun completeWithError() {
                observer.onError(LocationNotAvailable())
                observer.onComplete()
            }
        }, null)
    }
}