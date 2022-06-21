package com.burtaev.weather.feature.location.di

import android.location.Geocoder
import com.burtaev.weather.feature.location.domain.LocationInteractor
import com.burtaev.weather.feature.location.domain.LocationUseCase
import com.burtaev.weather.feature.location.presentation.LocationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val viewModelModule = module {
    viewModel {
        LocationViewModel(get(), get())
    }
}
private val useCaseModule = module {
    single { LocationInteractor(get(), get(), get()) as LocationUseCase }
}

private val geocoderModule = module {
    single { Geocoder(get()) }
}
var LocationModule = listOf(
    viewModelModule,
    useCaseModule,
    geocoderModule
)
