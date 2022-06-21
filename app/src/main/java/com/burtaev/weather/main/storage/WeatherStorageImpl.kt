package com.burtaev.weather.main.storage

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.burtaev.weather.feature.weather.presentation.DEFAULT_ID

const val ID_ADDRESS = "idAddress"
private const val WEATHER_SETTING = "weatherSetting"

class WeatherStorageImpl(context: Context) : WeatherStorage {
    private val sharedPreferences = context.getSharedPreferences(WEATHER_SETTING, MODE_PRIVATE)
    override fun saveId(id: Long) {
        val editor = sharedPreferences.edit()
        editor.putLong(ID_ADDRESS, id)
        editor.apply()
    }

    override fun getId(): Long {
        return sharedPreferences.getLong(ID_ADDRESS, DEFAULT_ID)
    }
}
