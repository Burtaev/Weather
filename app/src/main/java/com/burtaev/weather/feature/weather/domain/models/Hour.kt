package com.burtaev.weather.feature.weather.domain.models

data class Hour(
    val id: Int,
    val time: String,
    val temp: String,
    val condition: Condition
)
