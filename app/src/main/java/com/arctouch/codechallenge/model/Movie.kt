package com.arctouch.codechallenge.model

import com.google.gson.annotations.SerializedName

data class Movie(
        val id: Long,
        val title: String,
        val overview: String?,
        val genres: List<Genre>?,
        @SerializedName("genre_ids") val genreIds: List<Int>?,
        @SerializedName("poster_path") val posterPath: String?,
        @SerializedName("backdrop_path") val backdropPath: String?,
        @SerializedName("release_date") val releaseDate: String?
) {
    fun getAllGenre() = genres?.joinToString(separator = ", ") { it.name }
}