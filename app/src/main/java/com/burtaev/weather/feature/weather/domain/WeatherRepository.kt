package com.burtaev.weather.feature.weather.domain

import com.burtaev.weather.feature.weather.domain.models.Weather
import io.reactivex.rxjava3.core.Single

interface WeatherRepository {
    fun getWeather(latLng: String): Single<Weather>
}
