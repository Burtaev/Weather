package com.burtaev.weather.feature.weather.di

import com.burtaev.weather.main.sheduler.SchedulerProvider
import com.burtaev.weather.main.sheduler.SchedulerProviderImpl
import org.koin.dsl.module

private val schedulerModule = module {
    single { SchedulerProviderImpl() as SchedulerProvider }
}

var SchedulerModule = listOf(
    schedulerModule
)
