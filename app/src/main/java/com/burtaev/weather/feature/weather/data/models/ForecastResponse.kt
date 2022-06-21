package com.burtaev.weather.feature.weather.data.models

import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    @SerializedName("forecastday")
    val forecastdayResponse: List<ForecastdayResponse>
)
