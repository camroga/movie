package com.buildreams.themovies.thirdparty

import android.content.Context
import androidx.room.Room
import com.buildreams.themovies.data.local.MovieRoomDatabase
import com.buildreams.themovies.data.local.dao.MovieDao
import com.buildreams.themovies.data.source.LocalMovieDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {


    @Singleton // Tell Dagger-Hilt to create a singleton accessible everywhere in ApplicationCompenent (i.e. everywhere in the application)
    @Provides
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        MovieRoomDatabase::class.java,
        "the_movie_database"
    ).build()

    @Singleton
    @Provides
    fun provideMovieDao(db: MovieRoomDatabase) =
        db.getMovieDao() // The reason we can implement a Dao for the database

    @Provides
    @Singleton
    fun provideLocalMovieDataSource(movieDao: MovieDao): LocalMovieDataSource {
        return LocalMovieDataSource(movieDao)
    }
}
