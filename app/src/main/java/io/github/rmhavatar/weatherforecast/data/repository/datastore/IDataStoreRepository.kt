package io.github.rmhavatar.weatherforecast.data.repository.datastore

import kotlinx.coroutines.flow.Flow

interface IDataStoreRepository {
    suspend fun saveLastSearchedCityNameToDataStore(city: String)

    fun getLastSearchedCityNameFromDataStore(): Flow<String?>
}