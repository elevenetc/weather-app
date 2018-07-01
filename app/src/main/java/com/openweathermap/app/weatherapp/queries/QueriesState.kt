package com.openweathermap.app.weatherapp.queries

data class QueriesState(val type: Type, val queries: List<SearchQuery> = emptyList()) {
    enum class Type {
        PROGRESS, RESULT, EMPTY, DELETING_QUERIES, ALL_DELETED
    }
}