package io.github.rmhavatar.weatherforecast.data.api.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Geo data fields in Geocoding API response
 */
@JsonClass(generateAdapter = true)
data class GeocodingData(
    @Json(name = "name")
    val cityName: String,
    val lat: Double,
    val lon: Double
)
