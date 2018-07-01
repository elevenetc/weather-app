package com.openweathermap.app.weatherapp.search

import dagger.Module
import dagger.Provides

@Module
class SearchModule {
    @Provides
    fun viewModel(inst: SearchViewModelImpl): SearchViewModel = inst
}