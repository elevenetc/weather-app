package com.openweathermap.app.weatherapp.utils

import com.openweathermap.app.weatherapp.common.exceptions.NotFoundSearchQuery
import com.openweathermap.app.weatherapp.queries.SearchQuery
import com.openweathermap.app.weatherapp.queries.SearchQueryDao
import io.reactivex.Single

internal class InMemorySearchQueryDao : SearchQueryDao {

    private val queries = mutableListOf<SearchQuery>()
    private var id: Int = 0

    override fun getAll(): Single<List<SearchQuery>> {
        return Single.just(queries)
    }

    override fun getLatest(): Single<SearchQuery> {
        return if (queries.isEmpty()) {
            Single.error(NotFoundSearchQuery())
        } else {
            Single.just(queries.last())
        }
    }

    override fun getById(id: Int): Single<SearchQuery> {
        for (query in queries)
            if (query.id == id)
                return Single.just(query)
        return Single.error(NotFoundSearchQuery())
    }

    override fun insert(vararg queries: SearchQuery) {
        this.queries.addAll(queries)
    }

    override fun delete(query: SearchQuery) {
        for (q in queries)
            if (q.id == query.id) {
                queries.remove(q)
                break
            }
    }

    override fun deleteAll() {
        queries.clear()
    }

}