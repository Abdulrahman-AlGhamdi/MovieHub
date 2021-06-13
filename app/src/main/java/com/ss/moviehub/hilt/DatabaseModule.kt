package com.ss.moviehub.hilt

import android.content.Context
import androidx.room.Room
import com.ss.moviehub.database.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, MovieDatabase::class.java, MovieDatabase.DATABASE_NAME).build()

    @Provides
    @Singleton
    fun provideDao(database: MovieDatabase) = database.movieDao()
}