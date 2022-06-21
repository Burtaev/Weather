package com.burtaev.weather.feature.weather.data.models

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("location")
    val location: LocationResponse,
    @SerializedName("current")
    val currentResponse: CurrentResponse,
    @SerializedName("forecast")
    val forecastResponse: ForecastResponse,
)
