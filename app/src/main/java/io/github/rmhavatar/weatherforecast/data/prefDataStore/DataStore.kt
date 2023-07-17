package io.github.rmhavatar.weatherforecast.data.prefDataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import javax.inject.Inject


const val WEATHER_FORECAST_DATASTORE = "weather_forecast_datastore"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = WEATHER_FORECAST_DATASTORE)

class DataStoreManager @Inject constructor(private val context: Context) {

    companion object {
        val LAST_SEARCHED_CITY_KEY =
            stringPreferencesKey("LAST_SEARCHED_CITY_KEY")
    }

    suspend fun saveLastGeneratedTestPollIdsToDataStore(city: String) {
        context.dataStore.edit { setting ->
            setting[LAST_SEARCHED_CITY_KEY] = city
        }
    }

    fun getLastSearchedCityName() = context.dataStore.data.map { preferences ->
        preferences[LAST_SEARCHED_CITY_KEY]
    }
}