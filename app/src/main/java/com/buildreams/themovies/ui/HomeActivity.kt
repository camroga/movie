package com.buildreams.themortal.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.buildreams.themovies.ui.theme.TheMovieTheme
import com.buildreams.themovies.ui.movie.MovieViewModel
import com.buildreams.themovies.ui.movie.Movies
import com.buildreams.themovies.ui.movie.screen_state.MovieNetworkErrorInterpreter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {

    private val movieViewModel: MovieViewModel by viewModels()


    private lateinit var networkErrorInterpreter: MovieNetworkErrorInterpreter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        networkErrorInterpreter = MovieNetworkErrorInterpreter(context = applicationContext)

        // Start a coroutine in the lifecycle scope
        lifecycleScope.launch {
            // repeatOnLifecycle launches the block in a new coroutine every time the
            // lifecycle is in the STARTED state (or above) and cancels it when it's STOPPED.
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Trigger the flow and start listening for values.
                // Note that this happens when lifecycle is STARTED and stops
                // collecting when the lifecycle is STOPPED
                setContent {
                    val navController = rememberNavController()
                    TheMovieTheme {
                        // A surface container using the 'background' color from the theme
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            NavHost(navController = navController, startDestination = "main") {
                                // Navigating to the graph via its route ('login') automatically
                                // navigates to the graph's start destination - 'username'
                                // therefore encapsulating the graph's internal routing logic
                                movieGraph(navController)
                            }
                        }
                    }
                }

            }
        }
        //fetch items
        movieViewModel.fetchMovies()
    }

    private fun NavGraphBuilder.movieGraph(navController: NavController) {
        navigation(startDestination = "moviesScreen", route = "main") {
            composable("moviesScreen") {
                Movies(
                    navController,
                    movieViewModel
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TheMovieTheme {
        /*Movies(
            navController,
            movieViewModel
        )*/
    }
}