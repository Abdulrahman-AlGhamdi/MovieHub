package com.ss.moviehub.hilt

import com.ss.moviehub.api.MovieApiService
import com.ss.moviehub.database.MovieDao
import com.ss.moviehub.repository.DatabaseRepository
import com.ss.moviehub.repository.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMoviesRepository(apiService: MovieApiService) = MoviesRepository(apiService)

    @Provides
    @Singleton
    fun provideDatabaseRepository(movieDao: MovieDao) = DatabaseRepository(movieDao)
}