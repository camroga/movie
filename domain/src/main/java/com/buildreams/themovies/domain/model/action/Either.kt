package com.buildreams.themovies.domain.model.action


sealed interface Either<out R, out E> {
    data class Success<R>(val data: R) : Either<R, Nothing>

    data class Error<E>(val data: E) : Either<Nothing, E>
}
