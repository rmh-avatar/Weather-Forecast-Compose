package io.github.rmhavatar.weatherforecast.data.repository.forecast

import io.github.rmhavatar.weatherforecast.data.api.dto.WeatherResponseData
import io.github.rmhavatar.weatherforecast.data.util.ResponseState
import kotlinx.coroutines.flow.Flow

interface IForecastRepository {
    fun fetchWeatherDataByCityName(cityName: String): Flow<ResponseState<WeatherResponseData>>

    fun fetchWeatherDataByCoordinates(): Flow<ResponseState<WeatherResponseData>>
}