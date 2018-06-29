package com.openweathermap.app.weatherapp.common.navigation

import android.os.Bundle
import com.openweathermap.app.weatherapp.common.MainActivity

interface Navigator {
    fun goToRecentQueries(mainActivity: MainActivity)
    fun goToSearch(mainActivity: MainActivity, queryId: Int = -1)
    fun onMainActivityCreated(mainActivity: MainActivity, savedInstanceState: Bundle?)
}