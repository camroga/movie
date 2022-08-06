package com.buildreams.themovies.data.source

import com.buildreams.themovies.data.local.dao.MovieDao
import com.buildreams.themovies.data.mapper.toMovieEntityModel
import com.buildreams.themovies.data.mapper.toMovieModel
import com.buildreams.themovies.domain.model.Movie
import com.buildreams.themovies.domain.model.action.Either
import com.buildreams.themovies.domain.model.action.Either.Error
import com.buildreams.themovies.domain.model.action.Either.Success
import com.buildreams.themovies.domain.model.action.error.ErrorEntity.DataBaseError
import com.buildreams.themovies.domain.model.action.error.ErrorEntity.EmptyResponseError
import com.buildreams.themovies.domain.model.action.error.ErrorEntity.UnknownError
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class LocalMovieDataSource constructor(private val dao: MovieDao) {

    fun getTopRatedMovies(): Flow<Either> = flow {
        try {
            dao.getMovie()
                // to ensure that you only get notified when the data you’re interested in has changed
                .distinctUntilChanged()
                .map { result ->
                    if (result.isNullOrEmpty()) {
                        emit(Error(EmptyResponseError))
                    } else {
                        emit(Success(result.map { entity -> entity.toMovieModel() }))
                    }
                }
        } catch (e: Exception) {
            emit(Error(UnknownError(e)))
        }
    }.flowOn(IO) // Use the IO thread for this Flow

    suspend fun insertAllMovies(movies: List<Movie>): Flow<Either> = flow {
        try {
            val movieEntity = movies.map { movie -> movie.toMovieEntityModel() }
            dao.deleteAndInsert(movieEntity)
            emit(Success(true))
        } catch (e: Exception) {
            emit(Error(DataBaseError))
        }
    }.flowOn(IO) // Use the IO thread for this Flow

}
