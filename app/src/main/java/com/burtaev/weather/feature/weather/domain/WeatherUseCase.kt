package com.burtaev.weather.feature.weather.domain

import com.burtaev.weather.feature.location.database.domain.entities.AddressEntity
import com.burtaev.weather.feature.weather.domain.models.Weather
import io.reactivex.rxjava3.core.Single

interface WeatherUseCase {
    fun getResponse(latLng: String): Single<Weather>
    fun getAddressById(id: Long): Single<AddressEntity>
    fun getIdInStorage(): Long
}
