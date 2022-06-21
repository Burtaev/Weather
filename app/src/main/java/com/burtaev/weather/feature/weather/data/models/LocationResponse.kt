package com.burtaev.weather.feature.weather.data.models

import com.google.gson.annotations.SerializedName

data class LocationResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("localtime")
    val date: String
)
