package io.github.rmhavatar.weatherforecast.data.di

import android.app.Application
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
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
import io.github.rmhavatar.weatherforecast.data.repository.ForecastRepository
import io.github.rmhavatar.weatherforecast.data.repository.ISearchHistoricRepository
import io.github.rmhavatar.weatherforecast.data.repository.SearchHistoricRepository
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
    fun providesWebService(): IWebService = WebService.invoke()

    @Provides
    fun providesDatabase(application: Application): AppDatabase =
        AppDatabase.getDatabase(application)

    @Provides
    fun providesForecastRepository(
        webService: IWebService,
        locationTracker: LocationTracker
    ): ForecastRepository = ForecastRepository(webService, locationTracker)

    @Provides
    @Singleton
    fun providesSearchRepository(
        appDatabase: AppDatabase
    ): ISearchHistoricRepository = SearchHistoricRepository(appDatabase)
}