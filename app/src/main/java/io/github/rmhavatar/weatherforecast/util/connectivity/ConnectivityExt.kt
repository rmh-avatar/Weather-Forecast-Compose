package io.github.rmhavatar.weatherforecast.util.connectivity

import io.github.rmhavatar.weatherforecast.util.connectivity.base.IConnectivityProvider

/**
 * Detect if device has internet connexion
 */
fun IConnectivityProvider.NetworkState.hasInternet(): Boolean {
    return (this as? IConnectivityProvider.NetworkState.ConnectedState)?.hasInternet == true
}