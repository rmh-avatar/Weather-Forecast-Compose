package io.github.rmhavatar.weatherforecast.data.util

/**
 * Class to manage state of an operation
 */
sealed class ResponseState<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : ResponseState<T>(data = data)
    class Loading<T>(data: T? = null) : ResponseState<T>(data = data)
    class Error<T>(message: String?, data: T? = null) :
        ResponseState<T>(data = data, message = message)
}
