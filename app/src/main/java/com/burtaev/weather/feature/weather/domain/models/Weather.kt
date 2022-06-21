package com.burtaev.weather.feature.weather.domain.models

data class Weather(
    val location: Location,
    val current: Current,
    val forecast: Forecast,
)
