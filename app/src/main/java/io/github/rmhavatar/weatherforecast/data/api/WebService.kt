package io.github.rmhavatar.weatherforecast.data.api

import io.github.rmhavatar.weatherforecast.data.api.dto.GeocodingData
import io.github.rmhavatar.weatherforecast.data.api.dto.WeatherResponseData
import io.github.rmhavatar.weatherforecast.data.util.ResponseState
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException
import javax.inject.Inject

class WebService @Inject constructor(retrofit: Retrofit) : IWebService {
    private val serviceClient: ServiceClient by lazy {
        retrofit.create(ServiceClient::class.java)
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
                NOT_FOUND_CITY_ERROR_MESSAGE,
                responseErrorValue
            )

            500 -> ResponseState.Error(SERVER_ERROR_ERROR_MESSAGE, responseErrorValue)
            else -> ResponseState.Error(UNEXPECTED_ERROR_ERROR_MESSAGE, responseErrorValue)
        }
    }

    companion object {
        const val API_ID = "211a182a81e0424386f215c039d74026"
        const val UNKNOWN_RESPONSE_CODE = -1
        const val NOT_FOUND_CITY_ERROR_MESSAGE = "The city you are looking for is not found"
        const val SERVER_ERROR_ERROR_MESSAGE = "An error occurred on the server"
        const val UNEXPECTED_ERROR_ERROR_MESSAGE = "An unexpected error has occurred"
    }
}