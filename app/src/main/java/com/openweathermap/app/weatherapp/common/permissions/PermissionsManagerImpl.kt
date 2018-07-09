package com.openweathermap.app.weatherapp.common.permissions

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import com.openweathermap.app.weatherapp.common.RequestCodes
import javax.inject.Inject

class PermissionsManagerImpl @Inject constructor() : PermissionsManager {

    override fun isLocationGranted(requestCode: Int, permissions: Array<out String>, grantResults: IntArray): Boolean {
        return isPermissionGranted(RequestCodes.LOCATION_PERMISSION, Manifest.permission.ACCESS_FINE_LOCATION, requestCode, permissions, grantResults)
    }

    override fun isLocationGranted(fragment: Fragment): Boolean {
        return ContextCompat.checkSelfPermission(fragment.context!!, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    override fun allowedToAksLocPermission(fragment: Fragment): Boolean {
        return !fragment.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    override fun askLocPermissionDialog(fragment: Fragment) {
        fragment.requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), RequestCodes.LOCATION_PERMISSION)
    }

    override fun askLocPermissionDialogOrAppSettings(fragment: Fragment) {
        if (allowedToAksLocPermission(fragment)) {
            askLocPermissionDialog(fragment)
        } else {
            val intent = Intent().apply {
                action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                data = Uri.fromParts("package", fragment.context!!.packageName, null)
            }
            fragment.startActivity(intent)
        }
    }

    private fun isPermissionGranted(checkCode: Int, checkPermission: String, requestCode: Int, permissions: Array<out String>, grantResults: IntArray): Boolean {
        if (requestCode == checkCode) {
            for ((index, perm) in permissions.withIndex()) {
                if (perm == checkPermission)
                    if (grantResults.size > index && grantResults[index] == PackageManager.PERMISSION_GRANTED)
                        return true
                break
            }

        }
        return false
    }
}