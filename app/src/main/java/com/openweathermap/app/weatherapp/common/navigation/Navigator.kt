package com.openweathermap.app.weatherapp.common.navigation

import android.os.Bundle
import android.support.v4.app.FragmentActivity

interface Navigator {
    fun goToRecentQueries(mainActivity: FragmentActivity)
    fun goToSearch(mainActivity: FragmentActivity)
    fun onMainActivityCreated(mainActivity: FragmentActivity, savedInstanceState: Bundle?)
}