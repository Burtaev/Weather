package com.burtaev.weather.feature.location.domain

import android.location.Address
import com.burtaev.weather.feature.location.database.domain.entities.AddressEntity
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

interface LocationUseCase {
    fun getAddress(): Flowable<List<AddressEntity>>
    fun getAddressListByName(name: String): Single<List<Address>>
    fun setAddress(address: AddressEntity): Single<Long>
    fun delAddress(address: AddressEntity)
    fun saveIdInStorage(id: Long)
}
