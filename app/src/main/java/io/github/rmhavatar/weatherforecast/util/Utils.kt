package io.github.rmhavatar.weatherforecast.util

import android.location.LocationManager
import androidx.core.location.LocationManagerCompat
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/***
 * Helper to know whether phone's location is enabled or not
 */
fun isLocationEnabled(locationManager: LocationManager): Boolean {
    return LocationManagerCompat.isLocationEnabled(locationManager)
}

/***
 * Format time in Date to 12 hours format
 */
fun formatTime(date: Date): String {
    val dateFormatted = SimpleDateFormat.getTimeInstance(DateFormat.SHORT, Locale.US)
    return dateFormatted.format(date)
}

/***
 * Format datetime to 12 hours format
 */
fun formatDateTime(date: Date): String {
    val dateFormatted =
        SimpleDateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, Locale.US)
    return dateFormatted.format(date)
}

/***
 * Create url to download icon from Weather API
 */
fun getWeatherConditionUrl(icon: String): String {
    return "https://openweathermap.org/img/wn/${icon}@4x.png"
}