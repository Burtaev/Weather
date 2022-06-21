package com.burtaev.weather.feature.location.database.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.burtaev.weather.feature.location.database.domain.entities.AddressEntity
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

@Dao
interface AddressDao {
    @Query("SELECT * FROM addressEntity")
    fun getAll(): Flowable<List<AddressEntity>>

    @Query("SELECT * FROM addressEntity WHERE id LIKE :id")
    fun getById(id: Long): Single<AddressEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(address: AddressEntity): Single<Long>

    @Delete
    fun delete(address: AddressEntity)
}
