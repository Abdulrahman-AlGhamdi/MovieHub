package com.ss.moviehub.di.provide

import com.ss.moviehub.data.api.MovieApiService
import com.ss.moviehub.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object ProvideNetworkModule {

    @Provides
    fun provideMovieApiService(): MovieApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MovieApiService::class.java)
}