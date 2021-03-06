package com.openweathermap.app.weatherapp.common

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.openweathermap.app.weatherapp.App
import com.openweathermap.app.weatherapp.di.AppComponent
import io.reactivex.disposables.CompositeDisposable

open class BaseFragment : Fragment() {

    lateinit var appComponent: AppComponent

    lateinit var subs: CompositeDisposable

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent = (context.applicationContext as App).appComponent
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subs = CompositeDisposable()
    }

    override fun onDestroyView() {
        subs.dispose()
        super.onDestroyView()
    }
}