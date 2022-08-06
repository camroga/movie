package com.buildreams.themovies.data.remote

import com.buildreams.themovies.data.remote.dto.MovieResponse
import com.buildreams.themovies.data.remote.dto.PageResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface MovieRestApi {

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<PageResult<MovieResponse?>>
}