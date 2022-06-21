package com.burtaev.weather.feature.location.repository.domain

import com.burtaev.weather.feature.location.database.domain.entities.AddressEntity
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

interface AddressRepository {
    fun getAll(): Flowable<List<AddressEntity>>
    fun set(addressEntity: AddressEntity): Single<Long>
    fun getById(id: Long): Single<AddressEntity>
    fun del(addressEntity: AddressEntity)
}
