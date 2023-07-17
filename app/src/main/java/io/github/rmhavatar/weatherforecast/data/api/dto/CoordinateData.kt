package io.github.rmhavatar.weatherforecast.data.api.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * City coordinate
 */
@JsonClass(generateAdapter = true)
data class CoordinateData(
    @Json(name = "lon")
    val lon: Double,
    @Json(name = "lat")
    val lat: Double,
)