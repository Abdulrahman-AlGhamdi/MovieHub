package com.ss.moviehub.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "result_table")
data class Result(
    @PrimaryKey
    var id         : Int = 0,
    var adult      : Boolean = false,
    var overview   : String = "",
    var popularity : Double = 0.0,
    var title      : String = "",
    var video      : Boolean = false,
    var added      : Boolean = false,
    @SerializedName(value ="poster_path") var posterPath             : String = "",
    @SerializedName(value ="release_date") var releaseDate           : String = "",
    @SerializedName(value ="original_title") var originalTitle       : String = "",
    @SerializedName(value ="original_language") var originalLanguage : String = "",
    @SerializedName(value = "backdrop_path") var backdropPath        : String = "",
    @SerializedName(value ="vote_average") var voteAverage           : Double = 0.0,
    @SerializedName(value ="vote_count") var voteCount               : Int = 0
) : Parcelable