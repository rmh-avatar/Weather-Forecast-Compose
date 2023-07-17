package io.github.rmhavatar.weatherforecast.data.api.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Wind behavior in city
 */
@JsonClass(generateAdapter = true)
data class WindData(
    @Json(name = "speed")
    val speed: Float,
    @Json(name = "deg")
    val directionInDegree: Float?,
    @Json(name = "gust")
    val gustSpeed: Float?
)
