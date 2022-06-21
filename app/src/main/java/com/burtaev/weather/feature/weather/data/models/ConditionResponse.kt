package com.burtaev.weather.feature.weather.data.models

import com.google.gson.annotations.SerializedName

data class ConditionResponse(
    @SerializedName("text")
    val description: String,
    @SerializedName("icon")
    val iconUrl: String
)
