package com.buildreams.themovies.ui.movie.screen_state

import android.content.Context
import com.buildreams.themortal.R
import com.buildreams.themortal.util.NetworkErrorInterpreter

class MovieNetworkErrorInterpreter(private val context: Context) : NetworkErrorInterpreter {

    companion object {
        const val UNAUTHORIZED = 401
        const val MOVIE_NOT_FOUND = 404
    }

    override fun interpret(status: Int): String =
        when (status) {
            UNAUTHORIZED -> context.getString(R.string.movie_error_unauthorized)
            MOVIE_NOT_FOUND -> context.getString(R.string.movie_not_found)
            else -> context.getString(R.string.movie_error_unknown)
        }
}