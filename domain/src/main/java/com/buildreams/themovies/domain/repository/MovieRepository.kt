package com.buildreams.themovies.domain.repository

import com.buildreams.themovies.domain.model.Movie
import com.buildreams.themovies.domain.model.action.Either
import com.buildreams.themovies.domain.model.action.error.ErrorEntity
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun fetchTopRatedMovies(): Flow<Either<List<Movie>, ErrorEntity>>
    suspend fun getTopRatedMovies(): Flow<Either<List<Movie>, ErrorEntity>>
    suspend fun insertAllTopRatedMovies(movies: List<Movie>): Flow<Either<Boolean, ErrorEntity>>
}