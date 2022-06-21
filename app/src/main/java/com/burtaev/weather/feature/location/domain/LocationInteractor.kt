package com.burtaev.weather.feature.location.domain

import android.location.Address
import android.location.Geocoder
import com.burtaev.weather.feature.location.database.domain.entities.AddressEntity
import com.burtaev.weather.feature.location.repository.domain.AddressRepository
import com.burtaev.weather.main.storage.WeatherStorage
import io.reactivex.rxjava3.core.Single

private const val MAX_RESULTS = 3

class LocationInteractor(
    private val addressRepository: AddressRepository,
    private val geocoder: Geocoder,
    private val weatherStorage: WeatherStorage
) : LocationUseCase {
    override fun getAddress() = addressRepository.getAll()

    override fun setAddress(address: AddressEntity) = addressRepository.set(address)

    override fun delAddress(address: AddressEntity) = addressRepository.del(address)

    override fun getAddressListByName(name: String): Single<List<Address>> {
        return Single.fromCallable { geocoder.getFromLocationName(name, MAX_RESULTS) }
    }

    override fun saveIdInStorage(id: Long) {
        weatherStorage.saveId(id)
    }
}
