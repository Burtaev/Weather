package com.burtaev.weather.main.di

import com.burtaev.weather.feature.location.repository.data.AddressRepositoryImpl
import com.burtaev.weather.feature.location.repository.domain.AddressRepository
import com.burtaev.weather.feature.weather.data.repository.WeatherRepositoryImpl
import com.burtaev.weather.feature.weather.domain.WeatherRepository
import org.koin.dsl.module

private val repositoryModule = module {
    single { WeatherRepositoryImpl(get()) as WeatherRepository }
    single { AddressRepositoryImpl(get()) as AddressRepository }
}

val RepositoryModule = repositoryModule
