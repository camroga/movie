package com.buildreams.themovies.domain.model.action

import com.buildreams.themovies.domain.model.action.error.ErrorEntity


sealed interface Either {
    data class Success(private val data: Any) : Either {
        @Suppress("UNCHECKED_CAST")
        fun <R> getData() = data as R
    }

    data class Error(val error: ErrorEntity) : Either
}