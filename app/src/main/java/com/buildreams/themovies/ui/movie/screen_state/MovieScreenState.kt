package com.buildreams.themovies.ui.movie.screen_state

import com.buildreams.themovies.domain.model.Movie
import com.buildreams.themovies.domain.model.action.error.ErrorEntity

sealed interface MovieScreenState {
    object OnLoading : MovieScreenState
    data class OnMovieSaved(val isSaved: Boolean) : MovieScreenState
    data class OnMovieLoaded(val movies: List<Movie>) : MovieScreenState
    data class OnError(val error: ErrorEntity) : MovieScreenState
}

