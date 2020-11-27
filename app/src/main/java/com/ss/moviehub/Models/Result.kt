package com.ss.moviehub.Models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "result_table")
data class Result(
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    var adult: Boolean,
    var backdrop_path: String,
    var original_language: String,
    var original_title: String,
    var overview: String,
    var popularity: Double,
    var poster_path: String,
    var release_date: String,
    var title: String,
    var video: Boolean,
    var vote_average: Double,
    var vote_count: Int,
    var added: Boolean = false
) : Parcelable