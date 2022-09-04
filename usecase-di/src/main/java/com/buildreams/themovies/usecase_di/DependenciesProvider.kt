package com.doepiccoding.usecase_di

import com.buildreams.themovies.data.repository.MovieRepository
import com.buildreams.themovies.data.source.LocalMovieDataSource
import com.buildreams.themovies.data.source.RemoteMovieDataSource
import com.buildreams.themovies.domain.usecase.RatedMoviesUseCase
import com.buildreams.themovies.domain.usecase.SaveTopRatedMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DependenciesProvider {

    @Provides
    @Singleton
    fun provideGetTopRatedMoviesUseCase(
        remoteMovieDataSource: RemoteMovieDataSource,
        localMovieDataSource: LocalMovieDataSource
    ): RatedMoviesUseCase {
        val repository = MovieRepository(
            remoteMovieDataSource = remoteMovieDataSource,
            localMovieDataSource = localMovieDataSource
        )
        return RatedMoviesUseCase(repository)
    }

    @Provides
    fun provideSaveTopRatedMoviesUseCase(
        remoteMovieDataSource: RemoteMovieDataSource,
        localMovieDataSource: LocalMovieDataSource
    ): SaveTopRatedMoviesUseCase {
        val repository = MovieRepository(
            remoteMovieDataSource = remoteMovieDataSource,
            localMovieDataSource = localMovieDataSource
        )
        return SaveTopRatedMoviesUseCase(repository)
    }
}