package com.openweathermap.app.weatherapp.search

import dagger.Subcomponent

@Subcomponent(modules = [SearchModule::class])
interface SearchComponent {
    fun viewModel(): SearchViewModel
}