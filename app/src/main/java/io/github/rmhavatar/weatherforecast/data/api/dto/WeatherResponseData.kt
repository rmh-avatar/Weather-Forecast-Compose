package io.github.rmhavatar.weatherforecast.data.api.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import io.github.rmhavatar.weatherforecast.domain.api.dto.SunBehaviorData

/**
 * Weather data fields in API response
 */
@JsonClass(generateAdapter = true)
data class WeatherResponseData(
    @Json(name = "id")
    val id: Long,
    @Json(name = "name")
    val cityName: String,
    @Json(name = "coord")
    val coordinates: CoordinateData,
    @Json(name = "weather")
    val weatherCondition: List<WeatherConditionData>,
    @Json(name = "main")
    val temperature: TemperatureData,
    @Json(name = "wind")
    val wind: WindData,
    @Json(name = "dt")
    val forecastTime: Long,
    @Json(name = "sys")
    val sunBehavior: SunBehaviorData
)
