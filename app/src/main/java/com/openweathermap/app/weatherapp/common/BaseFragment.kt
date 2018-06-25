package com.openweathermap.app.weatherapp.common

import android.content.Context
import android.support.v4.app.Fragment
import com.openweathermap.app.weatherapp.App
import com.openweathermap.app.weatherapp.di.AppComponent
import io.reactivex.disposables.CompositeDisposable

open class BaseFragment : Fragment() {

    lateinit var appComponent: AppComponent

    val subs = CompositeDisposable()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent = (context.applicationContext as App).appComponent
    }

    override fun onDestroy() {
        subs.dispose()
        super.onDestroy()
    }
}