package com.openweathermap.app.weatherapp.common.scheduling

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SchedulersImpl @Inject constructor() : com.openweathermap.app.weatherapp.common.scheduling.Schedulers {
    override fun background(): Scheduler {
        return Schedulers.io()
    }

    override fun ui(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}