package com.buildreams.themovies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.buildreams.themovies.data.local.MovieRoomDatabase.Companion.VERSION
import com.buildreams.themovies.data.local.dao.MovieDao
import com.buildreams.themovies.data.local.model.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = VERSION,
    exportSchema = false
)
abstract class MovieRoomDatabase : RoomDatabase() {
    abstract fun getMovieDao(): MovieDao

    companion object {
        const val VERSION = 1
    }
}