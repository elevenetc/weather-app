package com.openweathermap.app.weatherapp

import android.os.Bundle
import com.openweathermap.app.weatherapp.common.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appComponent.navigator().onMainActivityCreated(this, savedInstanceState)
    }
}