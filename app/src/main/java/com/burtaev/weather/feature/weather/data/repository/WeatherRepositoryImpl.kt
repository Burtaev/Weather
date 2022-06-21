package com.burtaev.weather.feature.weather.data.repository

import com.burtaev.weather.feature.weather.data.api.WeatherApi
import com.burtaev.weather.feature.weather.domain.WeatherRepository
import com.burtaev.weather.feature.weather.domain.models.Condition
import com.burtaev.weather.feature.weather.domain.models.Current
import com.burtaev.weather.feature.weather.domain.models.Day
import com.burtaev.weather.feature.weather.domain.models.Forecast
import com.burtaev.weather.feature.weather.domain.models.Forecastday
import com.burtaev.weather.feature.weather.domain.models.Hour
import com.burtaev.weather.feature.weather.domain.models.Location
import com.burtaev.weather.feature.weather.domain.models.Weather
import io.reactivex.rxjava3.core.Single
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.Locale

private const val PREFIX_URL = "https:/"

class WeatherRepositoryImpl(private val weatherApi: WeatherApi) : WeatherRepository {
    override fun getWeather(latLng: String): Single<Weather> {
        val forecastDay = mutableListOf<Forecastday>()
        val hour = mutableListOf<Hour>()
        return weatherApi.getWeather(latLng).map {
            it.forecastResponse.forecastdayResponse.mapIndexed { indexF, _ ->
                it.forecastResponse.forecastdayResponse[indexF].hours.mapIndexed { indexH, _ ->
                    val hourItem = Hour(
                        id = indexH,
                        time = convertDateToTime(it.forecastResponse.forecastdayResponse[indexF].hours[indexH].timeEpoch),
                        temp = it.forecastResponse.forecastdayResponse[indexF].hours[indexH].temp.toDouble()
                            .toInt().toString() + "°",
                        condition = Condition(
                            description = it.forecastResponse.forecastdayResponse[indexF].hours[indexH].condition.description,
                            iconUrl = PREFIX_URL + it.forecastResponse.forecastdayResponse[indexF].hours[indexH].condition.iconUrl
                        )
                    )
                    hour.add(indexH, hourItem)
                }
                forecastDay.add(
                    indexF,
                    Forecastday(
                        id = indexF,
                        date = formatDate(it.forecastResponse.forecastdayResponse[indexF].date),
                        day = Day(
                            maxTemp = it.forecastResponse.forecastdayResponse[indexF].day.maxTemp.toDouble()
                                .toInt().toString() + "°",
                            minTemp = it.forecastResponse.forecastdayResponse[indexF].day.maxTemp.toDouble()
                                .toInt().toString() + "°",
                            condition = Condition(
                                description = it.forecastResponse.forecastdayResponse[indexF].day.conditionResponse.description,
                                iconUrl = PREFIX_URL + it.forecastResponse.forecastdayResponse[indexF].day.conditionResponse.iconUrl
                            )
                        ),
                        hours = hour
                    )
                )
            }
            Weather(
                location = Location(name = it.location.name, date = it.location.date),
                current = Current(
                    temp = it.currentResponse.temp.toDouble().toInt().toString() + "°",
                    feelslike = it.currentResponse.feelslike.toDouble().toInt().toString() + "°",
                    condition = Condition(
                        description = it.currentResponse.conditionResponse.description,
                        iconUrl = PREFIX_URL + it.currentResponse.conditionResponse.iconUrl
                    )
                ),
                forecast = Forecast(forecastDay)
            )
        }
    }
}

private fun convertDateToTime(millis: Long): String {
    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
    val date = Date(millis * 1000)
    return sdf.format(date)
}

private fun formatDate(millis: Long): String {
    val date = Date(millis * 1000)
    val calendar = GregorianCalendar.getInstance()
    calendar.time = date
    return calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
}
