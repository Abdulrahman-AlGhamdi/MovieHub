package com.ss.moviehub.Database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ss.moviehub.Models.Result

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAndUpdateMovie(result: Result): Long

    @Query("SELECT * FROM movies")
    fun getAllMovies(): LiveData<List<Result>>

    @Delete
    suspend fun deleteMovie(result: Result)
}