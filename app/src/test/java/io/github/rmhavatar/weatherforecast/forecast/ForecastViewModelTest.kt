package io.github.rmhavatar.weatherforecast.forecast

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.github.rmhavatar.weatherforecast.data.repository.datastore.DataStoreRepository
import io.github.rmhavatar.weatherforecast.data.repository.datastore.IDataStoreRepository
import io.github.rmhavatar.weatherforecast.data.repository.search_historic.ISearchHistoricRepository
import io.github.rmhavatar.weatherforecast.data.source.FakeDataStore
import io.github.rmhavatar.weatherforecast.data.source.FakeForecastRepository
import io.github.rmhavatar.weatherforecast.data.source.FakeSearchRepository
import io.github.rmhavatar.weatherforecast.search_historic.getOrAwaitValue
import io.github.rmhavatar.weatherforecast.ui.screen.forecast.ForecastViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ForecastViewModelTest {
    private val cityName = "Atlanta"

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    //Class under test
    private lateinit var forecastViewModel: ForecastViewModel

    private lateinit var searchHistoricRepository: ISearchHistoricRepository
    private lateinit var dataStoreRepository: IDataStoreRepository

    @Before
    fun setUp() {
        searchHistoricRepository = FakeSearchRepository()
        dataStoreRepository = DataStoreRepository(dataStore = FakeDataStore())
        forecastViewModel = ForecastViewModel(
            forecastRepository = FakeForecastRepository(),
            searchHistoricRepository = searchHistoricRepository,
            dataStoreRepository = dataStoreRepository
        )
    }

    @Test
    fun fetchWeatherData_hasLocationPermissionIsTrue_returnSameValueInAllRepositories() = runTest {
        forecastViewModel.fetchWeatherData(true)

        //Check viewModel has the last returned city weather data
        val weatherData = forecastViewModel.weatherDataFlow.first()
        assertEquals(cityName, weatherData?.data?.cityName)

        //Check last searched city name is the same name of the last returned city weather data
        val cityName = dataStoreRepository.getLastSearchedCityNameFromDataStore().first()
        assertEquals(cityName, cityName)

        //Check name of the first item in search historic is the same name of last returned city weather data
        val searchHistoric = searchHistoricRepository.allSearch.getOrAwaitValue()
        assertEquals(cityName, searchHistoric!![0].cityName)
    }

    @Test
    fun fetchWeatherData_hasLocationPermissionIsFalseAndThereIsASearchedCity_returnWeatherDataFromLastSearchedCity() =
        runTest {
            dataStoreRepository.saveLastSearchedCityNameToDataStore(cityName)

            forecastViewModel.fetchWeatherData(false)

            //Check viewModel has the last returned city weather data
            val weatherData = forecastViewModel.weatherDataFlow.first()
            assertEquals(cityName, weatherData?.data?.cityName)
        }

    @Test
    fun fetchWeatherData_hasLocationPermissionIsFalseAndThereIsNoASearchedCity_returnNull() =
        runTest {
            forecastViewModel.fetchWeatherData(false)

            //Check viewModel has the last returned city weather data
            val weatherData = forecastViewModel.weatherDataFlow.first()
            assertNull(weatherData)
        }

    @Test
    fun fetchWeatherDataByCityName_returnSameValueInAllRepositories() = runTest {
        forecastViewModel.fetchWeatherDataByCityName(cityName)

        //Check viewModel has the last returned city weather data
        val weatherData = forecastViewModel.weatherDataFlow.first()
        assertEquals(cityName, weatherData?.data?.cityName)

        //Check last searched city name is the same name of the last returned city weather data
        val cityName = dataStoreRepository.getLastSearchedCityNameFromDataStore().first()
        assertEquals(cityName, cityName)

        //Check name of the first item in search historic is the same name of last returned city weather data
        val searchHistoric = searchHistoricRepository.allSearch.getOrAwaitValue()
        assertEquals(cityName, searchHistoric!![0].cityName)
    }

    @Test
    fun fetchWeatherDataByCoordinates_returnSameValueInAllRepositories() = runTest {
        forecastViewModel.fetchWeatherDataByCoordinates()

        //Check viewModel has the last returned city weather data
        val weatherData = forecastViewModel.weatherDataFlow.first()
        assertEquals(cityName, weatherData?.data?.cityName)

        //Check last searched city name is the same name of the last returned city weather data
        val cityName = dataStoreRepository.getLastSearchedCityNameFromDataStore().first()
        assertEquals(cityName, cityName)

        //Check name of the first item in search historic is the same name of last returned city weather data
        val searchHistoric = searchHistoricRepository.allSearch.getOrAwaitValue()
        assertEquals(cityName, searchHistoric!![0].cityName)
    }
}