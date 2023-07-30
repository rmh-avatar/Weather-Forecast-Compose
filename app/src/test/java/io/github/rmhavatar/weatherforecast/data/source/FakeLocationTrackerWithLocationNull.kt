package io.github.rmhavatar.weatherforecast.data.source

import android.location.Location
import io.github.rmhavatar.weatherforecast.data.location.LocationTracker

class FakeLocationTrackerWithLocationNull : LocationTracker {
    override suspend fun getCurrentLocation(): Location? {
        return null
    }
}