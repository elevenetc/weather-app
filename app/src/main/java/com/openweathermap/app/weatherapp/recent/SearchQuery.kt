package com.openweathermap.app.weatherapp.recent

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "search-query")
class SearchQuery {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "query")
    var query: String = ""

    @ColumnInfo(name = "type")
    var type: Type = Type.GENERIC

    @ColumnInfo(name = "lat")
    var lat: Double = 0.0

    @ColumnInfo(name = "lon")
    var lon: Double = 0.0

    @ColumnInfo(name = "createdAt")
    var createdAt: Date = Date()

    enum class Type constructor(val id: Int) {

        GENERIC(0),
        NAME(1),
        LOCATION(2);

        companion object {
            fun fromId(id: Int): Type {
                for (type in values()) if (type.id == id) return type
                throw IllegalArgumentException("Invalid type id: $id")
            }
        }
    }
}