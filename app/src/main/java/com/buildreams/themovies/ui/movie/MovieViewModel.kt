package com.buildreams.themovies.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buildreams.themovies.domain.model.Movie
import com.buildreams.themovies.domain.model.action.Either.Error
import com.buildreams.themovies.domain.model.action.Either.Success
import com.buildreams.themovies.domain.usecase.GetTopRatedMoviesUseCase
import com.buildreams.themovies.domain.usecase.SaveTopRatedMoviesUseCase
import com.buildreams.themovies.ui.movie.screen_state.MovieScreenState
import com.buildreams.themovies.ui.movie.screen_state.MovieScreenState.OnError
import com.buildreams.themovies.ui.movie.screen_state.MovieScreenState.OnLoading
import com.buildreams.themovies.ui.movie.screen_state.MovieScreenState.OnMovieLoaded
import com.buildreams.themovies.ui.movie.screen_state.MovieScreenState.OnMovieSaved
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val saveTopRatedMoviesUseCase: SaveTopRatedMoviesUseCase
) : ViewModel() {

    private val _moviesState = MutableStateFlow<MovieScreenState>(OnLoading)

    // The UI collects from this StateFlow to get its state updates
    val moviesState = _moviesState.asStateFlow()

    fun fetchMovies() = viewModelScope.launch {
        //Call use case
        getTopRatedMoviesUseCase.invoke().collect { result ->
            when (result) {
                is Success -> _moviesState.emit(OnMovieLoaded(movies = result.getData()))
                is Error -> _moviesState.emit(OnError(result.error))
            }
        }
    }

    fun saveMovies(movies: List<Movie>) = viewModelScope.launch {
        //Call use case
        saveTopRatedMoviesUseCase.invoke(movies).collect { result ->
            when (result) {
                is Success -> _moviesState.emit(OnMovieSaved(result.getData()))
                is Error -> _moviesState.emit(OnError(result.error))
            }
        }
    }
}