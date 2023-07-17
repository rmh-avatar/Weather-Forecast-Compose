package io.github.rmhavatar.weatherforecast.util.connectivity.base

import android.os.Handler
import android.os.Looper

abstract class ConnectivityProviderBaseImpl : IConnectivityProvider {
    private val handler = Handler(Looper.getMainLooper())
    private val listeners = mutableSetOf<IConnectivityProvider.ConnectivityStateListener>()
    private var subscribed = false

    override fun addListener(listener: IConnectivityProvider.ConnectivityStateListener) {
        listeners.add(listener)
        listener.onStateChange(getNetworkState()) // propagate an initial state
        verifySubscription()
    }

    override fun removeListener(listener: IConnectivityProvider.ConnectivityStateListener) {
        listeners.remove(listener)
        verifySubscription()
    }

    private fun verifySubscription() {
        if (!subscribed && listeners.isNotEmpty()) {
            subscribe()
            subscribed = true
        } else if (subscribed && listeners.isEmpty()) {
            unsubscribe()
            subscribed = false
        }
    }

    protected fun dispatchChange(state: IConnectivityProvider.NetworkState) {
        handler.post {
            for (listener in listeners) {
                listener.onStateChange(state)
            }
        }
    }

    protected abstract fun subscribe()
    protected abstract fun unsubscribe()
}