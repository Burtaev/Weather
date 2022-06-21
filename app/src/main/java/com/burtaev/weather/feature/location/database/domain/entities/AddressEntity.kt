package com.burtaev.weather.feature.location.database.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AddressEntity(
//    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "lat") val lat: Double,
    @ColumnInfo(name = "lng") val lng: Double,
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
)
