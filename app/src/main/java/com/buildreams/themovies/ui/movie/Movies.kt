package com.buildreams.themovies.ui.movie

import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.buildreams.themortal.R
import com.buildreams.themovies.domain.model.Movie
import com.buildreams.themovies.domain.model.action.error.ErrorEntity
import com.buildreams.themovies.domain.model.action.error.ErrorEntity.DataBaseError
import com.buildreams.themovies.domain.model.action.error.ErrorEntity.EmptyResponseError
import com.buildreams.themovies.domain.model.action.error.ErrorEntity.NetworkError
import com.buildreams.themovies.domain.model.action.error.ErrorEntity.UnknownError
import com.buildreams.themovies.ui.components.CardsMovies
import com.buildreams.themovies.ui.movie.screen_state.MovieNetworkErrorInterpreter
import com.buildreams.themovies.ui.movie.screen_state.MovieScreenState.OnError
import com.buildreams.themovies.ui.movie.screen_state.MovieScreenState.OnLoading
import com.buildreams.themovies.ui.movie.screen_state.MovieScreenState.OnMovieLoaded
import com.buildreams.themovies.ui.movie.screen_state.MovieScreenState.OnMovieSaved

@Composable
fun Movies(
    navController: NavController,
    movieViewModel: MovieViewModel,
    networkErrorInterpreter: MovieNetworkErrorInterpreter
) {
    var movies by remember { mutableStateOf(emptyList<Movie>()) }

    when (val uiState = movieViewModel.moviesState.collectAsState().value) {
        is OnMovieLoaded -> {
            movies = uiState.movies
            movieViewModel.saveMovies(uiState.movies)
        }
        is OnError -> {
            Toast.makeText(
                LocalContext.current,
                getMessageFromError(uiState.error, networkErrorInterpreter),
                LENGTH_SHORT
            ).show()
            movieViewModel.getMovies()
        }
        OnLoading -> Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
        is OnMovieSaved -> Toast.makeText(
            LocalContext.current,
            stringResource(R.string.movie_saved),
            LENGTH_SHORT
        ).show()
    }
    CardsMovies(movies)

}

@Composable
private fun getMessageFromError(
    error: ErrorEntity,
    networkErrorInterpreter: MovieNetworkErrorInterpreter
): String =
    when (error) {
        EmptyResponseError -> stringResource(R.string.movie_error_no_response)
        DataBaseError -> stringResource(R.string.movie_error_data_base)
        is NetworkError -> networkErrorInterpreter.interpret(
            error.httpStatus
        )
        is UnknownError -> stringResource(
            R.string.movie_error_unknown,
            error.exception.message!!
        )
        else -> TODO()
    }