package com.ss.moviehub.di.provide

import android.content.Context
import androidx.room.Room
import com.ss.moviehub.data.database.MovieDao
import com.ss.moviehub.data.database.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProvideDatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): MovieDatabase = Room.databaseBuilder(
        context = context,
        klass = MovieDatabase::class.java,
        name = MovieDatabase.DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun provideDao(database: MovieDatabase): MovieDao = database.movieDao()
}