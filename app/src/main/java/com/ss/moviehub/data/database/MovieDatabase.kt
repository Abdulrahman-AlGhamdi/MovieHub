package com.ss.moviehub.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ss.moviehub.data.models.Result

@Database(entities = [Result::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        const val DATABASE_NAME = "movie database"
    }
}