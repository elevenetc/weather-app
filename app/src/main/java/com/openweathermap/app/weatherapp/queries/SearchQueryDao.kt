package com.openweathermap.app.weatherapp.queries

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Single


@Dao
interface SearchQueryDao {

    @get:Query("SELECT * FROM `search-query`")
    val all: Single<List<SearchQuery>>

    @Insert
    fun insert(vararg queries: SearchQuery)

    @Delete
    fun delete(query: SearchQuery)

    @Query("DELETE FROM `search-query`")
    fun deleteAll()
}