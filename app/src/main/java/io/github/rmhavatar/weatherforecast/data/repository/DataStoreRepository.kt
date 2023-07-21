package io.github.rmhavatar.weatherforecast.data.repository

import io.github.rmhavatar.weatherforecast.data.prefDataStore.IDataStore
import kotlinx.coroutines.flow.Flow

class DataStoreRepository(private val dataStore: IDataStore) : IDataStoreRepository {
    override suspend fun saveLastSearchedCityNameToDataStore(city: String) =
        dataStore.saveLastSearchedCityNameToDataStore(city)

    override fun getLastSearchedCityNameFromDataStore(): Flow<String?> =
        dataStore.getLastSearchedCityNameFromDataStore()
}