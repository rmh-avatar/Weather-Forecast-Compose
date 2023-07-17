package io.github.rmhavatar.weatherforecast.data.api

import io.github.rmhavatar.weatherforecast.data.api.dto.GeocodingData
import io.github.rmhavatar.weatherforecast.data.api.dto.WeatherResponseData
import io.github.rmhavatar.weatherforecast.data.util.ResponseState
import retrofit2.Response
import java.io.IOException

class WebService : IWebService {
    private val serviceClient: ServiceClient by lazy {
        ServiceGenerator().createService(
            ServiceClient::class.java
        )
    }

    override fun fetchWeatherDataByCityName(cityName: String): ResponseState<WeatherResponseData> {
        val geocodingDataByCityNameCall = serviceClient.fetchGeocodingDataByCityName(cityName)
        return try {
            val geocodingResponse: Response<List<GeocodingData>> =
                geocodingDataByCityNameCall.execute()
            if (geocodingResponse.isSuccessful) {
                val geocodingData = geocodingResponse.body()!!
                fetchWeatherDataByCoordinates(geocodingData[0].lon, geocodingData[0].lat)
            } else {
                getResponseError(geocodingResponse.code())
            }
        } catch (e: IOException) {
            getResponseError(UNKNOWN_RESPONSE_CODE)
        } catch (e: NullPointerException) {
            getResponseError(UNKNOWN_RESPONSE_CODE)
        }
    }

    override fun fetchWeatherDataByCoordinates(
        lon: Double,
        lat: Double
    ): ResponseState<WeatherResponseData> {
        val dataByCoordinatesCall = serviceClient.fetchWeatherDataByCoordinates(lon, lat)
        return try {
            val response: Response<WeatherResponseData> = dataByCoordinatesCall.execute()
            if (response.isSuccessful) {
                ResponseState.Success(response.body()!!)
            } else {
                getResponseError(response.code())
            }
        } catch (e: IOException) {
            getResponseError(UNKNOWN_RESPONSE_CODE)
        } catch (e: NullPointerException) {
            getResponseError(UNKNOWN_RESPONSE_CODE)
        }
    }

    private fun <T> getResponseError(
        responseCode: Int,
        responseErrorValue: T? = null,
    ): ResponseState.Error<T> {
        return when (responseCode) {
            404 -> ResponseState.Error(
                "The city you are looking for is not found",
                responseErrorValue
            )

            500 -> ResponseState.Error("An error occurred on the server", responseErrorValue)
            else -> ResponseState.Error("An unexpected error has occurred", responseErrorValue)
        }
    }

    companion object {
        const val API_ID = "211a182a81e0424386f215c039d74026"
        const val UNKNOWN_RESPONSE_CODE = -1

        @Volatile
        private var instance: WebService? = null
        private val LOCK = Any()

        operator fun invoke() = instance ?: synchronized(LOCK) {
            instance ?: WebService().also {
                instance = it
            }
        }
    }
}