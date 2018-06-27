package com.openweathermap.app.weatherapp.queries

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "search-query")
class SearchQuery {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "name")
    var name: String = ""

    @ColumnInfo(name = "zipCode")
    var zipCode: String = ""

    @ColumnInfo(name = "lat")
    var lat: Double = DEFAULT_LOC

    @ColumnInfo(name = "lon")
    var lon: Double = DEFAULT_LOC

    @ColumnInfo(name = "createdAt")
    var createdAt: Date = Date()

    fun hasLocation(): Boolean {
        return lat.toInt() != DEFAULT_LOC.toInt() && lon.toInt() != DEFAULT_LOC.toInt()
    }

    companion object {

        const val DEFAULT_LOC = 1000.0

        fun create(name: String = "", zip: String = "", lat: Double = DEFAULT_LOC, lon: Double = DEFAULT_LOC): SearchQuery {
            val result = SearchQuery()
            result.name = name
            result.zipCode = zip
            result.lat = lat
            result.lon = lon
            return result
        }
    }
}