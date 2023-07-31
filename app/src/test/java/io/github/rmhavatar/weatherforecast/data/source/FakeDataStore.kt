package io.github.rmhavatar.weatherforecast.data.source

import io.github.rmhavatar.weatherforecast.data.prefDataStore.IDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeDataStore : IDataStore {
    private var lastCity: String? = null
    override suspend fun saveLastSearchedCityNameToDataStore(city: String) {
        lastCity = city
    }

    override fun getLastSearchedCityNameFromDataStore(): Flow<String?> {
        return flow {
            lastCity
        }
    }
}