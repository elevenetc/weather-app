package com.openweathermap.app.weatherapp.common.scheduling

import io.reactivex.Scheduler

interface Schedulers {
    fun background(): Scheduler
    fun ui(): Scheduler
}