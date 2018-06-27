package com.openweathermap.app.weatherapp.recent

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query


@Dao
interface SearchQueryDao {

    @get:Query("SELECT * FROM `search-query`")
    val all: List<SearchQuery>

    @Insert
    fun insert(vararg users: SearchQuery)

    @Delete
    fun delete(user: SearchQuery)
}