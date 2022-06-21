package com.burtaev.weather

import android.app.Application
import com.burtaev.weather.feature.location.di.LocationModule
import com.burtaev.weather.feature.weather.di.SchedulerModule
import com.burtaev.weather.feature.weather.di.WeatherModule
import com.burtaev.weather.main.di.DataBaseModule
import com.burtaev.weather.main.di.NetworkModules
import com.burtaev.weather.main.di.RepositoryModule
import com.burtaev.weather.main.di.StorageModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(NetworkModules)
            modules(DataBaseModule)
            modules(RepositoryModule)
            modules(StorageModule)
            modules(SchedulerModule)
            modules(WeatherModule)
            modules(LocationModule)
        }
    }
}
