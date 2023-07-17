package io.github.rmhavatar.weatherforecast.data.api

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/***
 * Generate any client to use with Retrofit
 */
class ServiceGenerator {
    private var builder: Retrofit.Builder

    private var logging: HttpLoggingInterceptor

    private var httpClient: OkHttpClient.Builder

    private var retrofit: Retrofit

    init {
        val baseUrl = BASE_URL
        val responseServerTime = 60L
        val moshiBuilder = Moshi.Builder()

        builder = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshiBuilder.build()))

        logging = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BASIC)

        httpClient = OkHttpClient.Builder()
            .connectTimeout(responseServerTime, TimeUnit.SECONDS)

        retrofit = builder.build()
    }

    fun <S> createService(serviceClass: Class<S>): S {
        if (!httpClient.interceptors().contains(logging)) {
            httpClient.addInterceptor(logging)
            builder.client(httpClient.build())
            retrofit = builder.build()
        }
        return retrofit.create(serviceClass)
    }

    companion object {
        const val BASE_URL = "https://api.openweathermap.org"
    }
}