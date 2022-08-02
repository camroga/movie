package com.buildreams.themovies.ui.movie.screen_state

import com.buildreams.themovies.domain.model.Movie
import com.buildreams.themovies.domain.model.action.error.ErrorEntity

sealed interface MovieScreenState {
    object OnLoading : MovieScreenState
    data class OnMovieSaved(val isSaved: Boolean) : MovieScreenState
    data class OnMovieLoaded(val movies: List<Movie>) : MovieScreenState
    data class OnError(val error: ErrorEntity) : MovieScreenState
}


data class LoadingViewState<T>(
    val data: T,
    val loadType: LoadType = LoadType.Load,
    val failed: Boolean = false
) {
    val isRefreshing
        get() = loadType == LoadType.Refresh

    fun asFailure() = copy(loadType = LoadType.Idle, failed = true)

    fun asSuccess(input: T) =
        copy(loadType = LoadType.Idle, failed = false, data = input)

    fun asRefreshing() = copy(loadType = LoadType.Refresh)

    enum class LoadType {
        Idle, // nothing happening
        Load, // loading data, usually the first time
        Refresh // we are refreshing data - used for SwipeRefresh spinner if applicable
    }

    companion object {
        // builder functions if you need to generate a initial state
        fun <T> failure(data: T) =
            LoadingViewState(failed = true, loadType = LoadType.Idle, data = data)

        fun <T> success(data: T) =
            LoadingViewState(failed = false, loadType = LoadType.Idle, data = data)
    }
}
