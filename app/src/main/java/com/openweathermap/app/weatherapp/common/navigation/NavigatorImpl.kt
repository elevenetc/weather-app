package com.openweathermap.app.weatherapp.common.navigation

import android.os.Bundle
import android.support.v4.app.FragmentManager
import com.openweathermap.app.weatherapp.R
import com.openweathermap.app.weatherapp.common.MainActivity
import com.openweathermap.app.weatherapp.queries.QueriesFragment
import com.openweathermap.app.weatherapp.search.SearchFragment
import javax.inject.Inject

class NavigatorImpl @Inject constructor() : Navigator {

    override fun onMainActivityCreated(mainActivity: MainActivity, savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            mainActivity.base().supportFragmentManager
                    .beginTransaction()
                    .add(R.id.main_content_group, SearchFragment.newInstance(-1))
                    .commit()
        }
    }

    override fun goToRecentQueries(mainActivity: MainActivity) {
        mainActivity.base().supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_content_group, QueriesFragment.newInstance())
                .addToBackStack(null)
                .commit()

        mainActivity.setBackVisible(true)
    }

    override fun goToSearch(mainActivity: MainActivity, queryId: Int) {
        mainActivity.base().supportFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        mainActivity.base().supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_content_group, SearchFragment.newInstance(queryId))
                .commit()

        mainActivity.setBackVisible(false)
    }
}