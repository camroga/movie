package com.buildreams.themovies.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buildreams.themovies.domain.model.Movie
import com.buildreams.themovies.domain.model.action.Either.Error
import com.buildreams.themovies.domain.model.action.Either.Success
import com.buildreams.themovies.domain.usecase.RatedMoviesUseCase
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
    private val ratedMoviesUseCase: RatedMoviesUseCase,
    private val saveTopRatedMoviesUseCase: SaveTopRatedMoviesUseCase
) : ViewModel() {

    private val _moviesState = MutableStateFlow<MovieScreenState>(OnLoading)

    // The UI collects from this StateFlow to get its state updates
    val moviesState = _moviesState.asStateFlow()

    fun fetchMovies() = viewModelScope.launch {
        //Call use case
        ratedMoviesUseCase.fetchTopRatedMovies().collect { result ->
            when (result) {
                is Error -> _moviesState.emit(OnError(error = result.data))
                is Success -> _moviesState.emit(OnMovieLoaded(movies = result.data))
            }
        }
    }

    fun getMovies() = viewModelScope.launch {
        ratedMoviesUseCase.getTopRatedMovies().collect { result ->
            when (result) {
                is Error -> _moviesState.emit(OnError(error = result.data))
                is Success -> _moviesState.emit(OnMovieLoaded(movies = result.data))
            }
        }
    }

    fun saveMovies(movies: List<Movie>) = viewModelScope.launch {
        //Call use case
        saveTopRatedMoviesUseCase.invoke(movies).collect { result ->
            when (result) {
                is Success -> _moviesState.emit(OnMovieSaved(result.data))
                is Error -> _moviesState.emit(OnError(result.data))
            }
        }
    }
}