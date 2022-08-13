package com.buildreams.themovies.data.local.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id") val id: Int,

    @NonNull
    @ColumnInfo(name = "title") val title: String,

    @NonNull
    @ColumnInfo(name = "overview") val overview: String,

    @NonNull
    @ColumnInfo(name = "voteAverage") val voteAverage: Double,
)
