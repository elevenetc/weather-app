package com.openweathermap.app.weatherapp.common.permissions

import android.support.v4.app.Fragment

interface PermissionsManager {
    /**
     * Granted permission should be expected at {@link android.support.v4.app.Fragment##onRequestPermissionsResult}
     * with code {@link com.openweathermap.app.weatherapp.common.RequestCodes#LOCATION_PERMISSION}
     */
    fun checkLocationPermission(fragment: Fragment, alreadyGranted: () -> Unit)

    fun isLocationGranted(requestCode: Int, permissions: Array<out String>, grantResults: IntArray): Boolean
}