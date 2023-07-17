package io.github.rmhavatar.weatherforecast.data.api

import io.github.rmhavatar.weatherforecast.data.api.dto.WeatherResponseData
import io.github.rmhavatar.weatherforecast.data.util.ResponseState

interface IWebService {
    fun fetchWeatherDataByCityName(cityName: String): ResponseState<WeatherResponseData>

    fun fetchWeatherDataByCoordinates(
        lon: Double,
        lat: Double
    ): ResponseState<WeatherResponseData>
}