package com.buildreams.themovies.data.source

import com.buildreams.themovies.data.BuildConfig.API_KEY
import com.buildreams.themovies.data.mapper.toMovieModel
import com.buildreams.themovies.data.remote.MovieRestApi
import com.buildreams.themovies.domain.model.action.Either
import com.buildreams.themovies.domain.model.action.Either.Error
import com.buildreams.themovies.domain.model.action.Either.Success
import com.buildreams.themovies.domain.model.action.error.ErrorEntity.EmptyResponseError
import com.buildreams.themovies.domain.model.action.error.ErrorEntity.NetworkError
import com.buildreams.themovies.domain.model.action.error.ErrorEntity.UnknownError
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteMovieDataSource(private val api: MovieRestApi) {

    fun getTopRatedMovies(): Flow<Either> = flow {
        try {
            val response = api.getTopRatedMovies(apiKey = API_KEY, page = 1)
            if (response.isSuccessful) {
                response.body()?.results?.let { body ->
                    emit(Success(body.map { dto -> dto?.toMovieModel() }))
                } ?: emit(Error(EmptyResponseError))
            } else {
                emit(Error(NetworkError(response.code())))
            }
        } catch (e: Exception) {
            emit(Error(UnknownError(e)))
        }
    }.flowOn(IO) // Use the IO thread for this Flow

}
