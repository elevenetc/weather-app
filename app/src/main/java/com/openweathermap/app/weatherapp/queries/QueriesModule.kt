package com.openweathermap.app.weatherapp.queries

import dagger.Module
import dagger.Provides

@Module
class QueriesModule {
    @Provides
    fun viewModel(inst: QueriesViewModelImpl): QueriesViewModel = inst
}