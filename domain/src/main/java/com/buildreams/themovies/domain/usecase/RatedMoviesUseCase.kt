package com.buildreams.themovies.domain.usecase

import com.buildreams.themovies.domain.repository.MovieRepository

class RatedMoviesUseCase(private val movieRepository: MovieRepository) {
    suspend fun getTopRatedMovies() = movieRepository.getTopRatedMovies()
    suspend fun fetchTopRatedMovies() = movieRepository.fetchTopRatedMovies()
}
