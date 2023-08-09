package com.ss.moviehub.di.bind

import com.ss.moviehub.repository.details.DetailsRepository
import com.ss.moviehub.repository.details.DetailsRepositoryImpl
import com.ss.moviehub.repository.library.LibraryRepository
import com.ss.moviehub.repository.library.LibraryRepositoryImpl
import com.ss.moviehub.repository.movies.MoviesRepository
import com.ss.moviehub.repository.movies.MoviesRepositoryImpl
import com.ss.moviehub.repository.search.SearchRepository
import com.ss.moviehub.repository.search.SearchRepositoryImpl
import com.ss.moviehub.repository.settings.SettingsManager
import com.ss.moviehub.repository.settings.SettingsManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class BindViewModelModule {

    @Binds
    @ViewModelScoped
    abstract fun bindMoviesRepository(
        moviesRepositoryImpl: MoviesRepositoryImpl
    ): MoviesRepository

    @Binds
    @ViewModelScoped
    abstract fun bindSearchRepository(
        searchRepositoryImpl: SearchRepositoryImpl
    ): SearchRepository

    @Binds
    @ViewModelScoped
    abstract fun bindLibraryRepository(
        libraryRepositoryImpl: LibraryRepositoryImpl
    ): LibraryRepository

    @Binds
    @ViewModelScoped
    abstract fun bindDetailsRepository(
        detailsRepositoryImpl: DetailsRepositoryImpl
    ): DetailsRepository

    @Binds
    @ViewModelScoped
    abstract fun bindSettingsRepository(
        settingsManagerImpl: SettingsManagerImpl
    ): SettingsManager
}