package com.burtaev.weather.feature.weather.data.models

import com.google.gson.annotations.SerializedName

data class CurrentResponse(
    @SerializedName("temp_c")
    val temp: String,
    @SerializedName("feelslike_c")
    val feelslike: String,
    @SerializedName("condition")
    val conditionResponse: ConditionResponse
)
