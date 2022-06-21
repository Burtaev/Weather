package com.burtaev.weather.feature.location.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.burtaev.weather.feature.location.database.data.AddressDao
import com.burtaev.weather.feature.location.database.domain.entities.AddressEntity

@Database(entities = [AddressEntity::class], version = 1)
abstract class AddressDatabase : RoomDatabase() {
    abstract fun addressDao(): AddressDao
}
