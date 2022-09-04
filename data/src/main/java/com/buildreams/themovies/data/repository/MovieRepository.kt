package com.buildreams.themovies.data.repository

import com.buildreams.themovies.data.source.LocalMovieDataSource
import com.buildreams.themovies.data.source.RemoteMovieDataSource
import com.buildreams.themovies.domain.model.Movie
import com.buildreams.themovies.domain.model.action.Either
import com.buildreams.themovies.domain.model.action.error.ErrorEntity
import com.buildreams.themovies.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieRepository(
    private val remoteMovieDataSource: RemoteMovieDataSource,
    private val localMovieDataSource: LocalMovieDataSource
) :
    MovieRepository {
    override suspend fun fetchTopRatedMovies(): Flow<Either<List<Movie>, ErrorEntity>> =
        remoteMovieDataSource.getTopRatedMovies()

    override suspend fun getTopRatedMovies(): Flow<Either<List<Movie>, ErrorEntity>> =
        localMovieDataSource.getTopRatedMovies()

    override suspend fun insertAllTopRatedMovies(movies: List<Movie>) = flow {
        localMovieDataSource.insertAllMovies(movies = movies).collect {
            emit(it)
        }
    }

}
