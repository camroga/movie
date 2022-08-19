package com.buildreams.themovies.domain.usecase

import com.buildreams.themovies.domain.model.Movie
import com.buildreams.themovies.domain.repository.MovieRepository

class SaveTopRatedMoviesUseCase(private val movieRepository: MovieRepository) {
    suspend operator fun invoke(recipes: List<Movie>) =
        movieRepository.insertAllTopRatedMovies(recipes)
}
