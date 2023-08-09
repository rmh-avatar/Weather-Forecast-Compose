package io.github.rmhavatar.weatherforecast.data.source

import io.github.rmhavatar.weatherforecast.data.api.dto.CoordinateData
import io.github.rmhavatar.weatherforecast.data.api.dto.TemperatureData
import io.github.rmhavatar.weatherforecast.data.api.dto.WeatherResponseData
import io.github.rmhavatar.weatherforecast.data.api.dto.WindData
import io.github.rmhavatar.weatherforecast.data.repository.forecast.IForecastRepository
import io.github.rmhavatar.weatherforecast.data.util.ResponseState
import io.github.rmhavatar.weatherforecast.domain.api.dto.SunBehaviorData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeForecastRepository : IForecastRepository {
    private val weatherData: WeatherResponseData =
        WeatherResponseData(
            id = 1,
            temperature = TemperatureData(
                temperature = 88.29F,
                humidity = 60,
                feelLike = 95.77F,
                max = 91.04F,
                min = 85.42F,
                pressure = 1018F
            ),
            cityName = "Atlanta",
            coordinates = CoordinateData(
                lat = 33.749,
                lon = -84.388
            ),
            weatherCondition = emptyList(),
            forecastTime = 1690733194,
            sunBehavior = SunBehaviorData(
                sunrise = 1690714068,
                sunset = 1690763999
            ),
            wind = WindData(
                speed = 11.5F, gustSpeed = null, directionInDegree = null
            )
        )

    override fun fetchWeatherDataByCityName(cityName: String): Flow<ResponseState<WeatherResponseData>> {
        return flow {
            emit(ResponseState.Success(data = weatherData))
        }
    }

    override fun fetchWeatherDataByCoordinates(): Flow<ResponseState<WeatherResponseData>> {
        return flow {
            emit(ResponseState.Success(data = weatherData))
        }
    }
}