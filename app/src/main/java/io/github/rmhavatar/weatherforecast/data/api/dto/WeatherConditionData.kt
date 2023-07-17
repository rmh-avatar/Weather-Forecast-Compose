package io.github.rmhavatar.weatherforecast.data.api.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Weather condition in city
 */
@JsonClass(generateAdapter = true)
data class WeatherConditionData(
    @Json(name = "id")
    val id: Long,
    @Json(name = "main")
    val condition: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "icon")
    val icon: String
)
