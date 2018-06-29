package com.openweathermap.app.weatherapp

import android.os.Bundle
import android.view.MenuItem
import com.openweathermap.app.weatherapp.common.BaseActivity
import com.openweathermap.app.weatherapp.common.MainActivity

class MainActivityImpl : MainActivity, BaseActivity() {

    override fun setBackVisible(visible: Boolean) {
        supportActionBar!!.setDisplayHomeAsUpEnabled(visible)
    }

    override fun base(): BaseActivity {
        return this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appComponent.navigator().onMainActivityCreated(this, savedInstanceState)
    }

    override fun onSupportNavigateUp(): Boolean {
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (android.R.id.home == item.itemId) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            onBackPressed()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        super.onBackPressed()
    }
}