package com.buildreams.themovies.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val voteAverage: Double,
    val overview: String,
)
