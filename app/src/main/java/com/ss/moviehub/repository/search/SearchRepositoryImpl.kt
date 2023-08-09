package com.ss.moviehub.repository.search

import android.content.Context
import androidx.preference.PreferenceManager
import com.ss.moviehub.R
import com.ss.moviehub.data.api.MovieApiService
import com.ss.moviehub.repository.search.SearchRepository.ResponseStatus
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiService: MovieApiService
) : SearchRepository {

    override suspend fun getSearchedMovies(search: String, pageNumber: Int) = flow {
        this.emit(ResponseStatus.Loading)
        val response = apiService.getSearchedMovie(search, pageNumber)
        if (!response.isSuccessful) this.emit(ResponseStatus.Failed(response.message()))
        else response.body()?.results?.filter { !it.posterPath.isNullOrEmpty() }?.let {
            if (it.isNotEmpty()) this.emit(ResponseStatus.Successful(it))
            else emit(ResponseStatus.Failed(context.getString(R.string.error_no_result)))
        }
    }.flowOn(Dispatchers.IO)

    override fun setLastSearch(search: String) = PreferenceManager
        .getDefaultSharedPreferences(context).edit()
        .putString(LAST_SEARCH_KEY, search)
        .apply()

    override fun getLastSearch(): String {
        val preference = PreferenceManager.getDefaultSharedPreferences(context)
        return preference.getString(LAST_SEARCH_KEY, "") ?: ""
    }

    private companion object {
        const val LAST_SEARCH_KEY = "Searched Movie"
    }
}