package io.github.rmhavatar.weatherforecast.data.location

import android.location.Location

interface LocationTracker {
    suspend fun getCurrentLocation(): Location?
}