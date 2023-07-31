package io.github.rmhavatar.weatherforecast.data.repository

import android.location.Location
import com.squareup.moshi.Moshi
import io.github.rmhavatar.weatherforecast.data.api.WebService
import io.github.rmhavatar.weatherforecast.data.api.WebService.Companion.NOT_FOUND_CITY_ERROR_MESSAGE
import io.github.rmhavatar.weatherforecast.data.api.WebService.Companion.SERVER_ERROR_ERROR_MESSAGE
import io.github.rmhavatar.weatherforecast.data.api.WebService.Companion.UNEXPECTED_ERROR_ERROR_MESSAGE
import io.github.rmhavatar.weatherforecast.data.api.enqueueResponse
import io.github.rmhavatar.weatherforecast.data.location.LocationTracker
import io.github.rmhavatar.weatherforecast.data.repository.forecast.ForecastRepository
import io.github.rmhavatar.weatherforecast.data.util.ResponseState
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit


@RunWith(MockitoJUnitRunner::class)
class ForecastRepositoryTest {
    private val cityName = "Atlanta"
    private lateinit var mockWebServer: MockWebServer

    // Class under test
    private lateinit var forecastRepository: ForecastRepository
    private lateinit var forecastRepositoryWithLocationNull: ForecastRepository

    @Before
    fun createRepository() {
        mockWebServer = MockWebServer()
        mockWebServer.start(8080)

        val okHttpClient = HttpLoggingInterceptor().run {
            level = HttpLoggingInterceptor.Level.BODY
            OkHttpClient.Builder()
                .readTimeout(1L, TimeUnit.SECONDS)
                .addInterceptor(this)
                .build()
        }

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().build()))
            .build()


        val fakeLocation = mock<Location> {
            on { latitude } doReturn 33.7489924
            on { longitude } doReturn -84.3902644
        }
        val fakeLocationTracker = mock<LocationTracker> {
            onBlocking { getCurrentLocation() } doReturn fakeLocation
        }
        forecastRepository =
            ForecastRepository(
                webService = WebService(retrofit),
                locationTracker = fakeLocationTracker
            )

        val fakeLocationTrackerWithLocationNull = mock<LocationTracker> {
            onBlocking { getCurrentLocation() } doReturn null
        }
        forecastRepositoryWithLocationNull =
            ForecastRepository(
                webService = WebService(retrofit),
                locationTracker = fakeLocationTrackerWithLocationNull
            )
    }

    @Test
    fun `should fetch weather data of Atlanta correctly given 200 response`() = runTest {
        mockWebServer.enqueueResponse("geocoding_Atlanta.json", HttpURLConnection.HTTP_OK)
        mockWebServer.enqueueResponse("weather_data_by_name.json", HttpURLConnection.HTTP_OK)


        forecastRepository.fetchWeatherDataByCityName(cityName).collect {
            assertThat(it, `is`(instanceOf(ResponseState.Success::class.java)))
            assertNotNull(it.data)
            assertThat(it.data!!.cityName, IsEqual(cityName))
        }
    }

    @Test
    fun `should fetch geocoding data given not found city error`() = runTest {
        mockWebServer.enqueueResponse("geocoding_Atlanta.json", HttpURLConnection.HTTP_NOT_FOUND)

        forecastRepository.fetchWeatherDataByCityName(cityName).collect {
            assertThat(it, `is`(instanceOf(ResponseState.Error::class.java)))
            assertThat(it.message, IsEqual(NOT_FOUND_CITY_ERROR_MESSAGE))
        }
    }

    @Test
    fun `should fetch geocoding data given server error`() = runTest {
        mockWebServer.enqueueResponse(
            "geocoding_Atlanta.json",
            HttpURLConnection.HTTP_INTERNAL_ERROR
        )

        forecastRepository.fetchWeatherDataByCityName(cityName).collect {
            assertThat(it, `is`(instanceOf(ResponseState.Error::class.java)))
            assertThat(it.message, IsEqual(SERVER_ERROR_ERROR_MESSAGE))
        }
    }

    @Test
    fun `should fetch geocoding data given unexpected error caused by timeout error`() = runTest {
        mockWebServer.enqueueResponse(
            "geocoding_Atlanta.json",
            HttpURLConnection.HTTP_CLIENT_TIMEOUT
        )

        forecastRepository.fetchWeatherDataByCityName(cityName).collect {
            assertThat(it, `is`(instanceOf(ResponseState.Error::class.java)))
            assertThat(it.message, IsEqual(UNEXPECTED_ERROR_ERROR_MESSAGE))
        }
    }

    @Test
    fun `should fetch weather data by coordinates correctly given 200 response`() = runTest {
        mockWebServer.enqueueResponse("weather_data_by_name.json", HttpURLConnection.HTTP_OK)

        forecastRepository.fetchWeatherDataByCoordinates().collect {
            assertThat(it, `is`(instanceOf(ResponseState.Success::class.java)))
            assertNotNull(it.data)
            assertThat(it.data!!.cityName, IsEqual(cityName))
        }
    }

    @Test
    fun `should fetch weather data by coordinates given error when location is null`() = runTest {
        mockWebServer.enqueueResponse("weather_data_by_name.json", HttpURLConnection.HTTP_OK)

        forecastRepositoryWithLocationNull.fetchWeatherDataByCoordinates().collect {
            assertThat(it, `is`(instanceOf(ResponseState.Error::class.java)))
            assertThat(it.message, IsEqual("Your location is not available now"))
        }
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}