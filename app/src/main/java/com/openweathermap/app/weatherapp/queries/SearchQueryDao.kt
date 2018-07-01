package com.openweathermap.app.weatherapp.queries

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Single


@Dao
interface SearchQueryDao {

    @Query("SELECT * FROM `search-query` ORDER BY createdAt DESC")
    fun getAll(): Single<List<SearchQuery>>

    @Query("SELECT * FROM `search-query` ORDER BY createdAt DESC LIMIT 1")
    fun getLatest(): Single<SearchQuery>

    @Query("SELECT * FROM `search-query` WHERE id=:id")
    fun getById(id: Int): Single<SearchQuery>

    @Insert
    fun insert(vararg queries: SearchQuery)

    @Delete
    fun delete(query: SearchQuery)

    @Query("DELETE FROM `search-query`")
    fun deleteAll()
}