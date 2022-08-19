package com.buildreams.themovies.domain.usecase

import com.buildreams.themovies.domain.repository.MovieRepository

class GetTopRatedMoviesUseCase(private val movieRepository: MovieRepository) {
    suspend operator fun invoke() = movieRepository.getTopRatedMovies()
}
