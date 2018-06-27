package com.openweathermap.app.weatherapp.queries

import dagger.Subcomponent

@Subcomponent(modules = [QueriesModule::class])
interface QueriesComponent {
    fun viewModel(): QueriesViewModel
}