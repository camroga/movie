package com.buildreams.themovies.data.repository

import com.buildreams.themovies.data.source.LocalMovieDataSource
import com.buildreams.themovies.data.source.RemoteMovieDataSource
import com.buildreams.themovies.domain.model.Movie
import com.buildreams.themovies.domain.model.action.Either
import com.buildreams.themovies.domain.model.action.Either.Error
import com.buildreams.themovies.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.single

class MovieRepository(
    private val remoteMovieDataSource: RemoteMovieDataSource,
    private val localMovieDataSource: LocalMovieDataSource
) :
    MovieRepository {

    override suspend fun getTopRatedMovies(): Flow<Either> = flow {
        remoteMovieDataSource.getTopRatedMovies().collect { result ->
            if (result is Error) {
                emit(localMovieDataSource.getTopRatedMovies().single())
            } else {
                emit(result)
            }
        }
    }.flowOn(IO)

    override suspend fun insertAllTopRatedMovies(movies: List<Movie>): Flow<Either> = flow {
        localMovieDataSource.insertAllMovies(movies = movies)
    }

}
