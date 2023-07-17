package io.github.rmhavatar.weatherforecast.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.rmhavatar.weatherforecast.data.api.IWebService
import io.github.rmhavatar.weatherforecast.data.api.WebService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesWebService(): IWebService = WebService.invoke()
}