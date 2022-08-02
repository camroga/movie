package com.buildreams.themovies.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.buildreams.themovies.data.local.model.MovieEntity
import kotlinx.coroutines.flow.Flow


@Dao
abstract class MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(movies: List<MovieEntity>?)

    @Query("DELETE FROM movie")
    abstract fun delete()

    @Delete
    abstract fun deleteAll(movies: List<MovieEntity>?)

    @Query("SELECT * FROM movie")
    abstract fun getMovie(): Flow<List<MovieEntity>?>

    @Transaction
    open fun deleteAndInsert(movies: List<MovieEntity>?) {
        deleteAll(movies)
        insertAll(movies)
    }
}