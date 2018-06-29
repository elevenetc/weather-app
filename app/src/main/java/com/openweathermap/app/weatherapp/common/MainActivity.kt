package com.openweathermap.app.weatherapp.common

import android.support.annotation.StringRes

interface MainActivity {
    fun setTitle(@StringRes id: Int)
    fun setBackVisible(visible: Boolean)
    fun base(): BaseActivity
}