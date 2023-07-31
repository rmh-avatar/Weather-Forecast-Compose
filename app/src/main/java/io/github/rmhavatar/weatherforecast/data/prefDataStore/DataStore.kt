package io.github.rmhavatar.weatherforecast.data.prefDataStore

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map


const val WEATHER_FORECAST_DATASTORE = "weather_forecast_datastore"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = WEATHER_FORECAST_DATASTORE)

/***
 * Manager from datastore file
 */
class DataStoreManager(private val application: Application) : IDataStore {

    companion object {
        val LAST_SEARCHED_CITY_KEY =
            stringPreferencesKey("LAST_SEARCHED_CITY_KEY")
    }

    override suspend fun saveLastSearchedCityNameToDataStore(city: String) {
        application.dataStore.edit { setting ->
            setting[LAST_SEARCHED_CITY_KEY] = city
        }
    }

    override fun getLastSearchedCityNameFromDataStore() =
        application.dataStore.data.map { preferences ->
            preferences[LAST_SEARCHED_CITY_KEY]
        }
}