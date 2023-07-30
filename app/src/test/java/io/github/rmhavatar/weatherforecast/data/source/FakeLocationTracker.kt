package io.github.rmhavatar.weatherforecast.data.source

import android.location.Location
import io.github.rmhavatar.weatherforecast.data.location.LocationTracker

class FakeLocationTracker : LocationTracker {
    override suspend fun getCurrentLocation(): Location {
        return Location("GPS").apply {
            longitude = 80.0
            latitude = 50.0
        }
    }
}