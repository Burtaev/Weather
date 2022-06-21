package com.burtaev.weather.feature.location.repository.data

import com.burtaev.weather.feature.location.database.AddressDatabase
import com.burtaev.weather.feature.location.database.domain.entities.AddressEntity
import com.burtaev.weather.feature.location.repository.domain.AddressRepository
import io.reactivex.rxjava3.core.Flowable

class AddressRepositoryImpl(private val addressDatabase: AddressDatabase) : AddressRepository {

    override fun getAll(): Flowable<List<AddressEntity>> = addressDatabase.addressDao().getAll()

    override fun set(addressEntity: AddressEntity) =
        addressDatabase.addressDao().insert(addressEntity)

    override fun getById(id: Long) = addressDatabase.addressDao().getById(id)

    override fun del(addressEntity: AddressEntity) =
        addressDatabase.addressDao().delete(addressEntity)
}
