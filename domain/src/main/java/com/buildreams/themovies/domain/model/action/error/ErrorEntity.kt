package com.buildreams.themovies.domain.model.action.error

sealed interface ErrorEntity {
    data class UnknownError(val exception: Exception) : ErrorEntity
    data class NetworkError(val httpStatus: Int) : ErrorEntity
    object EmptyResponseError : ErrorEntity
    object DataBaseError : ErrorEntity
}