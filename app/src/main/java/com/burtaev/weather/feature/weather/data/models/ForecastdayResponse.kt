package com.burtaev.weather.feature.weather.data.models

import com.google.gson.annotations.SerializedName

data class ForecastdayResponse(
    @SerializedName("date_epoch")
    val date: Long,
    @SerializedName("day")
    val day: DayResponse,
    @SerializedName("hour")
    val hours: List<HourResponse>
)

data class DayResponse(
    @SerializedName("maxtemp_c")
    val maxTemp: String,
    @SerializedName("mintemp_c")
    val minTemp: String,
    @SerializedName("condition")
    val conditionResponse: ConditionResponse
)
