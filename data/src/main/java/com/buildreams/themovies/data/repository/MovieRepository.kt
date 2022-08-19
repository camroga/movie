package com.buildreams.themovies.data.repository

import com.buildreams.themovies.data.source.LocalMovieDataSource
import com.buildreams.themovies.data.source.RemoteMovieDataSource
import com.buildreams.themovies.domain.model.Movie
import com.buildreams.themovies.domain.model.action.Either.Error
import com.buildreams.themovies.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MovieRepository(
    private val remoteMovieDataSource: RemoteMovieDataSource,
    private val localMovieDataSource: LocalMovieDataSource
) :
    MovieRepository {

    override suspend fun getTopRatedMovies() = flow {
        remoteMovieDataSource.getTopRatedMovies().collect { result ->
            if (result is Error) {
                localMovieDataSource.getTopRatedMovies().collect { localResult ->
                    emit(localResult)
                }
            } else {
                emit(result)
            }
        }
    }.flowOn(IO)

    override suspend fun insertAllTopRatedMovies(movies: List<Movie>) = flow {
        localMovieDataSource.insertAllMovies(movies = movies).collect {
            emit(it)
        }
    }

}
