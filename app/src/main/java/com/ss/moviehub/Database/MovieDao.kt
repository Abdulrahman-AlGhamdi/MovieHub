package com.ss.moviehub.Database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ss.moviehub.Models.Result

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAndUpdateMovie(result: Result)

    @Delete
    suspend fun deleteMovie(result: Result)

    @Query("SELECT * FROM result_table ORDER BY title ASC")
    fun getLibraryMovies(): LiveData<List<Result>>

    @Query("DELETE FROM result_table")
    suspend fun deleteAllMovies()
}