package com.ss.moviehub.data.database

import androidx.room.*
import com.ss.moviehub.models.Result
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAndUpdateMovie(result: Result)

    @Delete
    suspend fun deleteMovie(result: Result)

    @Query("SELECT * FROM result_table ORDER BY title ASC")
    fun getLibraryMovies(): Flow<List<Result>>

    @Query("DELETE FROM result_table")
    suspend fun deleteAllMovies()
}