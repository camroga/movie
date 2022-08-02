package com.buildreams.themovies.domain.repository

import com.buildreams.themovies.domain.model.Movie
import com.buildreams.themovies.domain.model.action.Either
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun insertAllTopRatedMovies(movies: List<Movie>): Flow<Either>
    suspend fun getTopRatedMovies(): Flow<Either>
}