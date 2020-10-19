package com.ss.moviehub.Model

data class Movie(
    val id: Int,
    val title: String,
    val release_date: String,
    val overview: String,
    val status: String,
    val runtime: Int,
    val original_language: String,
)