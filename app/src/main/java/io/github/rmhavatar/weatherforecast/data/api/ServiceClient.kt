package io.github.rmhavatar.weatherforecast.data.api

import io.github.rmhavatar.weatherforecast.data.api.dto.GeocodingData
import io.github.rmhavatar.weatherforecast.data.api.dto.WeatherResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceClient {
    @GET("/geo/1.0/direct")
    fun fetchGeocodingDataByCityName(
        @Query("q") cityName: String,
        @Query("appid") apiId: String = WebService.API_ID,
    ): Call<List<GeocodingData>>

    @GET("/data/2.5/weather")
    fun fetchWeatherDataByCoordinates(
        @Query("lon") lon: Double,
        @Query("lat") lat: Double,
        @Query("appid") apiId: String = WebService.API_ID,
        @Query("units") units: String = "imperial"
    ): Call<WeatherResponseData>
}