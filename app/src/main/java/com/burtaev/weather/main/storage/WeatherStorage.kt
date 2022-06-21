package com.burtaev.weather.main.storage

interface WeatherStorage {
    fun saveId(id: Long)
    fun getId(): Long
}
