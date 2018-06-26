package com.openweathermap.app.weatherapp.common.scheduling

import java.util.concurrent.Executor

class CurrentThreadExecutor : Executor {
    override fun execute(runnable: Runnable) {
        runnable.run()
    }
}