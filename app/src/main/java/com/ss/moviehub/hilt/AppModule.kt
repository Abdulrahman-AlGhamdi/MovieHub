package com.ss.moviehub.hilt

import android.content.Context
import com.ss.moviehub.api.MovieApiService
import com.ss.moviehub.database.MovieDao
import com.ss.moviehub.repository.DatabaseRepository
import com.ss.moviehub.repository.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMoviesRepository(
        @ApplicationContext context: Context,
        apiService: MovieApiService
    ) = MoviesRepository(context, apiService)

    @Provides
    @Singleton
    fun provideDatabaseRepository(movieDao: MovieDao) = DatabaseRepository(movieDao)
}