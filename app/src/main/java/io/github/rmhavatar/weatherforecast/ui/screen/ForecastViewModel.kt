package io.github.rmhavatar.weatherforecast.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.rmhavatar.weatherforecast.data.api.dto.WeatherResponseData
import io.github.rmhavatar.weatherforecast.data.prefDataStore.DataStoreManager
import io.github.rmhavatar.weatherforecast.data.repository.ForecastRepository
import io.github.rmhavatar.weatherforecast.data.util.ResponseState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val forecastRepository: ForecastRepository,
    private val dataStoreManager: DataStoreManager
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
                            dataStoreManager.saveLastSearchedCityNameToDataStore(it.data.cityName)
                        }
                        _weatherDataFlow.value = it
                    }
                } else {
                    dataStoreManager.getLastSearchedCityNameFromDataStore().collect { cityName ->
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
                    dataStoreManager.saveLastSearchedCityNameToDataStore(cityName)
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
                        dataStoreManager.saveLastSearchedCityNameToDataStore(it.data.cityName)
                    }
                    _weatherDataFlow.value = it
                }
            } catch (e: Exception) {
                _weatherDataFlow.value = ResponseState.Error("Unexpected Error")
            }
        }
    }
}