package com.ss.moviehub.di

import android.content.Context
import com.ss.moviehub.data.api.MovieApiService
import com.ss.moviehub.data.database.MovieDao
import com.ss.moviehub.repository.library.LibraryRepository
import com.ss.moviehub.repository.movies.MoviesRepository
import com.ss.moviehub.repository.search.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMoviesRepository(
        apiService: MovieApiService
    ): MoviesRepository = MoviesRepository(apiService)

    @Provides
    @Singleton
    fun provideSearchRepository(
        @ApplicationContext context: Context,
        apiService: MovieApiService
    ): SearchRepository = SearchRepository(context, apiService)

    @Provides
    @Singleton
    fun provideLibraryRepository(
        movieDao: MovieDao
    ): LibraryRepository = LibraryRepository(movieDao)
}