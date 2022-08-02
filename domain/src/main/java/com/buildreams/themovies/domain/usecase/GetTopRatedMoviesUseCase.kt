package com.buildreams.themovies.domain.usecase

import com.buildreams.themovies.domain.model.action.Either
import com.buildreams.themovies.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetTopRatedMoviesUseCase(private val movieRepository: MovieRepository) {
    suspend operator fun invoke(): Flow<Either> = movieRepository.getTopRatedMovies()
}
