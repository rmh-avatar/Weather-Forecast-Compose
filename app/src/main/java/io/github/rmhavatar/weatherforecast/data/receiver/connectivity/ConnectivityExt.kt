package io.github.rmhavatar.weatherforecast.data.receiver.connectivity

import io.github.rmhavatar.weatherforecast.data.receiver.connectivity.base.IConnectivityProvider

/**
 * Detect if device has internet connexion
 */
fun IConnectivityProvider.NetworkState.hasInternet(): Boolean {
    return (this as? IConnectivityProvider.NetworkState.ConnectedState)?.hasInternet == true
}