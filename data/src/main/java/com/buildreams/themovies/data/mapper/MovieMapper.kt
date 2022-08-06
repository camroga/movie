package com.buildreams.themovies.data.mapper

import com.buildreams.themovies.data.local.model.MovieEntity
import com.buildreams.themovies.data.remote.dto.MovieResponse
import com.buildreams.themovies.domain.model.Movie

fun MovieResponse.toMovieModel() =
    Movie(
        id = this.id,
        title = this.title,
        voteAverage = this.voteAverage,
        overview = this.overview,
        posterPath = this.posterPath,
        backdropPath = this.backdropPath
    )

fun Movie.toMovieEntityModel() =
    MovieEntity(
        id = this.id,
        title = this.title,
        voteAverage = this.voteAverage!!,
        overview = this.overview
    )

fun MovieEntity.toMovieModel() =
    Movie(
        id = this.id,
        title = this.title,
        voteAverage = this.voteAverage,
        overview = this.overview
    )