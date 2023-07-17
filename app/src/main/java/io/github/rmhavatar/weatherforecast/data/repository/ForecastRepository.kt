package io.github.rmhavatar.weatherforecast.data.repository

import io.github.rmhavatar.weatherforecast.data.api.WebService
import io.github.rmhavatar.weatherforecast.data.api.dto.WeatherResponseData
import io.github.rmhavatar.weatherforecast.data.location.LocationTracker
import io.github.rmhavatar.weatherforecast.data.util.ResponseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ForecastRepository @Inject constructor(
    private val webService: WebService,
    private val locationTracker: LocationTracker
) {
    fun fetchWeatherDataByCityName(cityName: String): Flow<ResponseState<WeatherResponseData>> {
        return flow {
            emit(webService.fetchWeatherDataByCityName(cityName))
        }.flowOn(Dispatchers.IO)
    }

    fun fetchWeatherDataByCoordinates(): Flow<ResponseState<WeatherResponseData>> {
        return flow {
            val location = locationTracker.getCurrentLocation()
            if (location == null) {
                emit(ResponseState.Error("Your location is not available now"))
            } else {
                emit(
                    webService.fetchWeatherDataByCoordinates(
                        location.longitude,
                        location.latitude
                    )
                )
            }
        }.flowOn(Dispatchers.IO)
    }
}