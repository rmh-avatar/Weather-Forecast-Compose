package io.github.rmhavatar.weatherforecast.data.prefDataStore

import kotlinx.coroutines.flow.Flow

interface IDataStore {
    suspend fun saveLastSearchedCityNameToDataStore(city: String)

    fun getLastSearchedCityNameFromDataStore(): Flow<String?>
}