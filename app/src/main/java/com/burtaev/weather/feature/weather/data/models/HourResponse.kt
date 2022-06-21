package com.burtaev.weather.feature.weather.data.models

import com.google.gson.annotations.SerializedName

data class HourResponse(
    @SerializedName("time_epoch")
    val timeEpoch: Long,
    @SerializedName("time")
    val time: String,
    @SerializedName("temp_c")
    val temp: String,
    @SerializedName("condition")
    val condition: ConditionResponse
)
