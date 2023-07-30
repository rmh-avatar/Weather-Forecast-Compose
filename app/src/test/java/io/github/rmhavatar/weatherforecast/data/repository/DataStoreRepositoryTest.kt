package io.github.rmhavatar.weatherforecast.data.repository

import io.github.rmhavatar.weatherforecast.data.source.FakeDataStore
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Test


class DataStoreRepositoryTest {
    private val lastCity = "Atlanta"

    // Class under test
    private lateinit var dataStoreRepository: DataStoreRepository


    @Before
    fun createRepository() {
        dataStoreRepository = DataStoreRepository(dataStore = FakeDataStore())
    }

    @Test
    fun saveLastSearchedCityNameToDataStoreAndGetItFromDataStore() = runTest {
        dataStoreRepository.saveLastSearchedCityNameToDataStore(city = lastCity)
        dataStoreRepository.getLastSearchedCityNameFromDataStore().collect { city ->
            assertThat(city, IsEqual(lastCity))
        }
    }
}