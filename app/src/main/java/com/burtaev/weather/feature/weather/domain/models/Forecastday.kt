package com.burtaev.weather.feature.weather.domain.models

data class Forecastday(
    val id: Int,
    val date: String,
    val day: Day,
    val hours: List<Hour>
)

data class Day(
    val maxTemp: String,
    val minTemp: String,
    val condition: Condition
)
