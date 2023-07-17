package io.github.rmhavatar.weatherforecast.util.connectivity

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import io.github.rmhavatar.weatherforecast.util.connectivity.base.ConnectivityProviderBaseImpl
import io.github.rmhavatar.weatherforecast.util.connectivity.base.IConnectivityProvider.NetworkState
import io.github.rmhavatar.weatherforecast.util.connectivity.base.IConnectivityProvider.NetworkState.ConnectedState
import io.github.rmhavatar.weatherforecast.util.connectivity.base.IConnectivityProvider.NetworkState.NotConnectedState

class ConnectivityProvider(private val cm: ConnectivityManager) :
    ConnectivityProviderBaseImpl() {

    private val networkCallback = ConnectivityCallback()

    override fun subscribe() {
        cm.registerDefaultNetworkCallback(networkCallback)
    }

    override fun unsubscribe() {
        cm.unregisterNetworkCallback(networkCallback)
    }

    override fun getNetworkState(): NetworkState {
        val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
        return if (capabilities != null) {
            ConnectedState.Connected(capabilities)
        } else {
            NotConnectedState
        }
    }

    private inner class ConnectivityCallback : ConnectivityManager.NetworkCallback() {

        override fun onCapabilitiesChanged(network: Network, capabilities: NetworkCapabilities) {
            dispatchChange(ConnectedState.Connected(capabilities))
        }

        override fun onLost(network: Network) {
            dispatchChange(NotConnectedState)
        }
    }
}