package io.github.rmhavatar.weatherforecast.data.api.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Temperature in city
 */
@JsonClass(generateAdapter = true)
data class TemperatureData(
    @Json(name = "temp")
    val temperature: Float,
    @Json(name = "feel_like")
    val feelLike: Float?,
    @Json(name = "temp_min")
    val min: Float,
    @Json(name = "temp_max")
    val max: Float,
    @Json(name = "pressure")
    val pressure: Float,
    @Json(name = "humidity")
    val humidity: Int
)
