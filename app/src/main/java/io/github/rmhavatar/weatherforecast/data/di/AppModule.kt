package io.github.rmhavatar.weatherforecast.data.di

import android.app.Application
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.rmhavatar.weatherforecast.data.api.WebService
import io.github.rmhavatar.weatherforecast.data.location.DefaultLocationTracker
import io.github.rmhavatar.weatherforecast.data.location.LocationTracker
import io.github.rmhavatar.weatherforecast.data.prefDataStore.DataStoreManager
import io.github.rmhavatar.weatherforecast.data.repository.ForecastRepository
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
    fun providesDataStoreManager(application: Application): DataStoreManager =
        DataStoreManager(application)

    @Provides
    @Singleton
    fun providesWebService(): WebService = WebService.invoke()

    @Provides
    @Singleton
    fun providesForecastRepository(
        webService: WebService,
        locationTracker: LocationTracker
    ): ForecastRepository = ForecastRepository(webService, locationTracker)
}