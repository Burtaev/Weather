package com.burtaev.weather.feature.weather.di

import com.burtaev.weather.feature.weather.data.api.WeatherApi
import com.burtaev.weather.feature.weather.domain.WeatherInteractor
import com.burtaev.weather.feature.weather.domain.WeatherUseCase
import com.burtaev.weather.feature.weather.presentation.WeatherViewModel
import com.burtaev.weather.main.di.createRetrofitService
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val viewModelModule = module {
    viewModel {
        WeatherViewModel(get(), get())
    }
}

private val useCaseModule = module {
    single {
        WeatherInteractor(get(), get(), get()) as WeatherUseCase
    }
}

private val retrofitModule = module {
    factory { createRetrofitService<WeatherApi>(get()) }
}

var WeatherModule = listOf(
    viewModelModule,
    retrofitModule,
    useCaseModule
)
