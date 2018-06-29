package com.openweathermap.app.weatherapp.common.navigation

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import com.openweathermap.app.weatherapp.R
import com.openweathermap.app.weatherapp.queries.QueriesFragment
import com.openweathermap.app.weatherapp.search.SearchFragment
import javax.inject.Inject

class NavigatorImpl @Inject constructor() : Navigator {

    override fun onMainActivityCreated(mainActivity: FragmentActivity, savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            mainActivity.supportFragmentManager
                    .beginTransaction()
                    .add(R.id.main_content_group, SearchFragment.newInstance(-1))
                    .commit()
        }
    }

    override fun goToRecentQueries(mainActivity: FragmentActivity) {
        mainActivity.supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_content_group, QueriesFragment.newInstance())
                .addToBackStack(null)
                .commit()
    }

    override fun goToSearch(mainActivity: FragmentActivity, queryId: Int) {
        mainActivity.supportFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        mainActivity.supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_content_group, SearchFragment.newInstance(queryId))
                .commit()
    }
}