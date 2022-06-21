package com.burtaev.weather.main.di

import com.burtaev.weather.main.storage.WeatherStorage
import com.burtaev.weather.main.storage.WeatherStorageImpl
import org.koin.dsl.module

private val storageModule = module {
    single { WeatherStorageImpl(get()) as WeatherStorage }
}

val StorageModule = storageModule
