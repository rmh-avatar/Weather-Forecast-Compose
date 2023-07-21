package io.github.rmhavatar.weatherforecast.ui.screen.forecast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.rmhavatar.weatherforecast.data.api.dto.WeatherResponseData
import io.github.rmhavatar.weatherforecast.data.db.entity.SearchEntity
import io.github.rmhavatar.weatherforecast.data.repository.IDataStoreRepository
import io.github.rmhavatar.weatherforecast.data.repository.IForecastRepository
import io.github.rmhavatar.weatherforecast.data.repository.ISearchHistoricRepository
import io.github.rmhavatar.weatherforecast.data.util.ResponseState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val forecastRepository: IForecastRepository,
    private val searchHistoricRepository: ISearchHistoricRepository,
    private val dataStoreRepository: IDataStoreRepository
) :
    ViewModel() {
    private val _weatherDataFlow: MutableStateFlow<ResponseState<WeatherResponseData>?> =
        MutableStateFlow(null)
    val weatherDataFlow = _weatherDataFlow.asStateFlow()

    /***
     * Fetch weather data from current location if hasLocationPermission is true
     * or from searched last city if hasLocationPermission is false
     */
    fun fetchWeatherData(hasLocationPermission: Boolean) {
        viewModelScope.launch {
            try {
                _weatherDataFlow.value = ResponseState.Loading()
                if (hasLocationPermission) {
                    forecastRepository.fetchWeatherDataByCoordinates().collect {
                        if (it.data?.cityName?.isNotBlank() == true) {
                            searchHistoricRepository.insert(
                                SearchEntity(
                                    it.data.cityName.hashCode().toLong(),
                                    it.data.cityName,
                                    Date()
                                )
                            )
                            dataStoreRepository.saveLastSearchedCityNameToDataStore(it.data.cityName)
                        }
                        _weatherDataFlow.value = it
                    }
                } else {
                    dataStoreRepository.getLastSearchedCityNameFromDataStore().collect { cityName ->
                        if (cityName.isNullOrBlank()) {
                            _weatherDataFlow.value = null
                        } else {
                            forecastRepository.fetchWeatherDataByCityName(cityName).collect {
                                _weatherDataFlow.value = it
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                _weatherDataFlow.value = ResponseState.Error("Unexpected Error")
            }
        }
    }

    fun fetchWeatherDataByCityName(cityName: String) {
        viewModelScope.launch {
            try {
                _weatherDataFlow.value = ResponseState.Loading()
                forecastRepository.fetchWeatherDataByCityName(cityName).collect {
                    searchHistoricRepository.insert(
                        SearchEntity(
                            cityName.hashCode().toLong(),
                            cityName,
                            Date()
                        )
                    )
                    dataStoreRepository.saveLastSearchedCityNameToDataStore(cityName)
                    _weatherDataFlow.value = it
                }
            } catch (e: Exception) {
                _weatherDataFlow.value = ResponseState.Error("Unexpected Error")
            }
        }
    }

    fun fetchWeatherDataByCoordinates() {
        viewModelScope.launch {
            try {
                _weatherDataFlow.value = ResponseState.Loading()
                forecastRepository.fetchWeatherDataByCoordinates().collect {
                    if (it.data?.cityName?.isNotBlank() == true) {
                        searchHistoricRepository.insert(
                            SearchEntity(
                                it.data.cityName.hashCode().toLong(), it.data.cityName, Date()
                            )
                        )
                        dataStoreRepository.saveLastSearchedCityNameToDataStore(it.data.cityName)
                    }
                    _weatherDataFlow.value = it
                }
            } catch (e: Exception) {
                _weatherDataFlow.value = ResponseState.Error("Unexpected Error")
            }
        }
    }
}