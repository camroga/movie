package com.buildreams.themovies.domain.model

data class Movie(
    val id: Int? = -1,
    val title: String = "",
    val voteAverage: Double? = 0.0,
    val overview: String = "",
    val posterPath: String? = null,
    val backdropPath: String? = null
)
