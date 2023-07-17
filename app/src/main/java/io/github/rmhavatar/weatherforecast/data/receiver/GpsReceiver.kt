package io.github.rmhavatar.weatherforecast.data.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.LocationManager

/**
 * Allow know when gps phone change the status
 */
class GpsReceiver(private val locationCallBack: LocationCallBack) : BroadcastReceiver() {
    interface LocationCallBack {
        fun turnedOn()
        fun turnedOff()
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null) {
            val locationManager: LocationManager =
                context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) locationCallBack.turnedOn() else locationCallBack.turnedOff()
        }
    }
}