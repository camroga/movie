package com.buildreams.themovies.domain.model.action


/*sealed interface Either {
    data class Success(private val data: Any) : Either {
        fun <R> getData() = data as R
    }

    data class Error(val error: ErrorEntity) : Either
}*/

//sealed interface EitherBeta {
//    data class Error<E>(val error: E) : EitherBeta
//    data class Success<R>(val value: R) : EitherBeta}
//
//    inline fun <reified T : Any> EitherBeta.success() = value
//}


sealed interface Either<out R, out E> {
    data class Success<R>(val data: R) : Either<R, Nothing>

    data class Error<E>(val data: E) : Either<Nothing, E>
}
