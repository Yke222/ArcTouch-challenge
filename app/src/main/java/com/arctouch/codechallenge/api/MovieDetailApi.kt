package com.arctouch.codechallenge.api

import com.arctouch.codechallenge.model.GenreResponse
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.model.UpcomingMoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interface for Details.
 */
interface DetailsApi {

    @GET("movie/{id}")
    fun movie(
            @Path("id") id: Long,
            @Query("api_key") apiKey: String,
            @Query("language") language: String
    ): Call<Movie>
}

/**
 * Interface for genre.
 */
interface GenreApi {

    @GET("genre/movie/list")
    fun genres(
            @Query("api_key") apiKey: String,
            @Query("language") language: String
    ): Call<GenreResponse>
}

/**
 * Interface for upcoming movies.
 */
interface UpcomingMoviesApi {

    @GET("movie/upcoming")
    fun upcomingMovies(
            @Query("api_key") apiKey: String,
            @Query("language") language: String,
            @Query("page") page: Long,
            @Query("region") region: String
    ): Call<UpcomingMoviesResponse>
}