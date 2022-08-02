package com.buildreams.themovies.ui.movie

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.buildreams.themortal.R
import com.buildreams.themovies.ui.movie.screen_state.MovieScreenState
import com.buildreams.themovies.domain.model.Movie
import com.buildreams.themovies.domain.model.action.error.ErrorEntity

@Composable
fun Movies(navController: NavController, movieViewModel: MovieViewModel) {
    val uiState by movieViewModel.moviesState.collectAsState()
    when (uiState) {
        //TODO review data class example
        is MovieScreenState.OnMovieLoaded -> Cards((uiState as MovieScreenState.OnMovieLoaded).movies)
        is MovieScreenState.OnError -> handleErrorFetchingMovies((uiState as MovieScreenState.OnError).error)
        MovieScreenState.OnLoading ->
            Box(Modifier.size(20.dp), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        is MovieScreenState.OnMovieSaved -> handleMoviesSaved()
    }

}

@Composable
private fun handleMoviesSaved() {
    SnackBar(stringResource(R.string.movie_saved))
}

@Composable
private fun handleErrorFetchingMovies(error: ErrorEntity) {
    SnackBar(getMessageFromError(error))
}

@Composable
private fun SnackBar(text: String) {
    Snackbar(modifier = Modifier.padding(8.dp)) {
        Text(text = text)
    }
}

@Composable
private fun getMessageFromError(error: ErrorEntity): String =
    when (error) {
        ErrorEntity.EmptyResponseError -> stringResource(R.string.movie_error_no_response)
        ErrorEntity.DataBaseError -> stringResource(R.string.movie_error_data_base)
//        is ErrorEntity.NetworkError -> networkErrorInterpreter.interpret(error.httpStatus)
        is ErrorEntity.UnknownError -> stringResource(
            R.string.movie_error_unknown,
            error.exception.message!!
        )
        else -> TODO()
    }

@Composable
fun Cards(movies: List<Movie>) {
    Column(Modifier.fillMaxWidth()) {
        movies.forEach { movie ->
            Column(Modifier.fillMaxWidth()) {
                /*if (movie.image.isNotEmpty()) {
                    Image(
                        painter = rememberAsyncImagePainter(movie.image),
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth()
                    )
                }*/
                Text(text = movie.title)
                Text(text = movie.voteAverage.toString())
                Text(text = movie.overview)
            }
        }
    }
}