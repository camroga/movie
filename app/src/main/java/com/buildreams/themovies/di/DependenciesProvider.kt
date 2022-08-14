package com.buildreams.themovies.di

import android.content.Context
import com.buildreams.themovies.ui.movie.screen_state.MovieNetworkErrorInterpreter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DependenciesProvider {

    @Provides
    @Singleton
    fun provideMovieNetworkErrorInterpreter(@ApplicationContext context: Context): MovieNetworkErrorInterpreter {
        return MovieNetworkErrorInterpreter(context)
    }
}