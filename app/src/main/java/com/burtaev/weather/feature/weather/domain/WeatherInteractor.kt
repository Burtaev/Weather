package com.burtaev.weather.feature.weather.domain

import com.burtaev.weather.feature.location.repository.domain.AddressRepository
import com.burtaev.weather.main.storage.WeatherStorage

class WeatherInteractor(
    private val weatherRepository: WeatherRepository,
    private val addressRepository: AddressRepository,
    private val weatherStorage: WeatherStorage
) : WeatherUseCase {
    override fun getResponse(latLng: String) = weatherRepository.getWeather(latLng)
    override fun getAddressById(id: Long) = addressRepository.getById(id)
    override fun getIdInStorage() = weatherStorage.getId()
}
