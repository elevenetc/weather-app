package com.openweathermap.app.weatherapp.common.permissions

import android.Manifest
import android.content.pm.PackageManager
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import com.openweathermap.app.weatherapp.common.RequestCodes
import javax.inject.Inject

class PermissionsManagerImpl @Inject constructor() : PermissionsManager {
    override fun checkLocationPermission(fragment: Fragment, alreadyGranted: () -> Unit) {
        if (ContextCompat.checkSelfPermission(fragment.context!!, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            fragment.requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), RequestCodes.LOCATION_PERMISSION)
        } else {
            alreadyGranted()
        }
    }
}