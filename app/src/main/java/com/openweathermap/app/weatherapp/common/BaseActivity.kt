package com.openweathermap.app.weatherapp.common

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.openweathermap.app.weatherapp.App
import com.openweathermap.app.weatherapp.di.AppComponent

open class BaseActivity : AppCompatActivity() {

    lateinit var appComponent: AppComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent = (application as App).appComponent
    }
}