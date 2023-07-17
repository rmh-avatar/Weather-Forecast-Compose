package io.github.rmhavatar.weatherforecast.domain.api.dto

import com.squareup.moshi.JsonClass

/**
 * Sun behavior in city (Sunset and Sunrise)
 */
@JsonClass(generateAdapter = true)
data class SunBehaviorData(
    val sunrise: Long,
    val sunset: Long
)
