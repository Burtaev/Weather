package com.burtaev.weather.feature.weather.domain.models

data class Current(
    val temp: String,
    val feelslike: String,
    val condition: Condition
)
