package com.buildreams.themovies.data.remote.dto

import com.google.gson.annotations.SerializedName

//import com.squareup.moshi.Json

data class PageResult<T>(
    @SerializedName("page")
    var page: Int = 0,
    @SerializedName("total_results")
    var totalResults: Int = 0,
    @SerializedName("total_pages")
    var totalPages: Int = 0,
    @SerializedName("results")
    var results: List<T>? = null
)

data class MovieResponse(
    @SerializedName("adult")
    var adult: Boolean?,
    @SerializedName("backdrop_path")
    var backdropPath: String?,
    @SerializedName("budget")
    var budget: Int? = null,
//    @SerializedName("genres")
//    var genres: List<Genre>? = null,
//    @SerializedName("genre_ids")
//    var genreIds: List<Int>? = null,
    @SerializedName("homepage")
    var homepage: String?,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("imdb_id")
    var imdbId: String?,
    @SerializedName("original_language")
    var originalLanguage: String?,
    @SerializedName("original_title")
    var originalTitle: String?,
    @SerializedName("overview")
    var overview: String?,
    @SerializedName("popularity")
    var popularity: Double? = null,
    @SerializedName("poster_path")
    var posterPath: String?,
    @SerializedName("release_date")
    var releaseDate: String?,
    @SerializedName("revenue")
    var revenue: Int? = null,
    @SerializedName("runtime")
    var runtime: Int? = null,
//    @SerializedName("spoken_languages")
//    var spokenLanguages: List<SpokenLanguage>? = null,
    @SerializedName("status")
    var status: String?,
    @SerializedName("tagline")
    var tagline: String?,
    @SerializedName("title")
    var title: String?,
    @SerializedName("video")
    var video: Boolean?,
    @SerializedName("vote_average")
    var voteAverage: Double? = null,
    @SerializedName("vote_count")
    var voteCount: Int? = null
)
