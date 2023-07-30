package io.github.rmhavatar.weatherforecast.data.di

import android.app.Application
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.rmhavatar.weatherforecast.data.api.IWebService
import io.github.rmhavatar.weatherforecast.data.api.WebService
import io.github.rmhavatar.weatherforecast.data.db.db.AppDatabase
import io.github.rmhavatar.weatherforecast.data.location.DefaultLocationTracker
import io.github.rmhavatar.weatherforecast.data.location.LocationTracker
import io.github.rmhavatar.weatherforecast.data.prefDataStore.DataStoreManager
import io.github.rmhavatar.weatherforecast.data.prefDataStore.IDataStore
import io.github.rmhavatar.weatherforecast.data.repository.DataStoreRepository
import io.github.rmhavatar.weatherforecast.data.repository.ForecastRepository
import io.github.rmhavatar.weatherforecast.data.repository.IDataStoreRepository
import io.github.rmhavatar.weatherforecast.data.repository.IForecastRepository
import io.github.rmhavatar.weatherforecast.data.repository.ISearchHistoricRepository
import io.github.rmhavatar.weatherforecast.data.repository.SearchHistoricRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesFusedLocationProviderClient(
        application: Application
    ): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(application)

    @Provides
    @Singleton
    fun providesLocationTracker(
        fusedLocationProviderClient: FusedLocationProviderClient,
        application: Application
    ): LocationTracker = DefaultLocationTracker(
        fusedLocationProviderClient = fusedLocationProviderClient,
        application = application
    )

    @Provides
    @Singleton
    fun providesDataStoreManager(application: Application): IDataStore =
        DataStoreManager(application)

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        val okHttpClient = HttpLoggingInterceptor().run {
            level = HttpLoggingInterceptor.Level.BODY
            OkHttpClient.Builder()
                .addInterceptor(this)
                .connectTimeout(60L, TimeUnit.SECONDS)
                .build()
        }

        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().build()))
            .build()
    }

    @Provides
    fun providesWebService(retrofit: Retrofit): IWebService = WebService(retrofit)

    @Provides
    fun providesDatabase(application: Application): AppDatabase =
        AppDatabase.getDatabase(application)

    @Provides
    fun providesForecastRepository(
        webService: IWebService,
        locationTracker: LocationTracker
    ): IForecastRepository = ForecastRepository(webService, locationTracker)

    @Provides
    fun providesSearchRepository(
        appDatabase: AppDatabase
    ): ISearchHistoricRepository = SearchHistoricRepository(appDatabase)

    @Provides
    fun providesDataStoreRepository(
        dataStore: IDataStore
    ): IDataStoreRepository = DataStoreRepository(dataStore)
}