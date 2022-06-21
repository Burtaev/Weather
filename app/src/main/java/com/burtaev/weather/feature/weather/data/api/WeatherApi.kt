package com.burtaev.weather.feature.weather.data.api

import com.burtaev.weather.feature.weather.data.models.WeatherResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("forecast.json")
    fun getWeather(
        @Query("q") latLng: String,
        @Query("days") days: Int = 7,
        @Query("lang") lang: String = "ru"
    ): Single<WeatherResponse>
}
