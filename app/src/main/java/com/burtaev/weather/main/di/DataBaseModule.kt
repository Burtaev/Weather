package com.burtaev.weather.main.di

import android.content.Context
import androidx.room.Room
import com.burtaev.weather.feature.location.database.AddressDatabase
import org.koin.dsl.module

const val DATABASE_NAME = "Address.db"
private val databaseModule = module {
    single { provideDataBase(get()) }
}

private fun provideDataBase(context: Context): AddressDatabase {
    return Room.databaseBuilder(
        context,
        AddressDatabase::class.java, DATABASE_NAME
    ).build()
}

val DataBaseModule = listOf(
    databaseModule
)
