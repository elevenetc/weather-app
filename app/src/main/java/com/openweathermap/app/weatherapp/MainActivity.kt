package com.openweathermap.app.weatherapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.openweathermap.app.weatherapp.search.SearchFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.main_content_group, SearchFragment.newInstance()).commit()
        }
    }
}