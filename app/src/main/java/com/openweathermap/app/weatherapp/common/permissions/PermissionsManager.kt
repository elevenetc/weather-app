package com.openweathermap.app.weatherapp.common.permissions

import android.support.v4.app.Fragment

interface PermissionsManager {
    fun askLocPermissionDialog(fragment: Fragment)
    fun askLocPermissionDialogOrAppSettings(fragment: Fragment)
    fun isLocationGranted(requestCode: Int, permissions: Array<out String>, grantResults: IntArray): Boolean
    fun isLocationGranted(fragment: Fragment): Boolean
    fun allowedToAksLocPermission(fragment: Fragment): Boolean
}