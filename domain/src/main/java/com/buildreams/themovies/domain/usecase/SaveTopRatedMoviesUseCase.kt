package com.buildreams.themovies.domain.usecase

import com.buildreams.themovies.domain.model.Movie
import com.buildreams.themovies.domain.repository.MovieRepository
import com.buildreams.themovies.domain.model.action.Either
import kotlinx.coroutines.flow.Flow

class SaveTopRatedMoviesUseCase(private val movieRepository: MovieRepository) {
    suspend operator fun invoke(recipes: List<Movie>): Flow<Either> =
        movieRepository.insertAllTopRatedMovies(recipes)
}
