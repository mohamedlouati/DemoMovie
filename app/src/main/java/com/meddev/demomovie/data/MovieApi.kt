package com.meddev.demomovie.data

import com.google.gson.JsonObject
import com.meddev.demomovie.models.MoviesItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 *Mohamed
 */
interface MovieApi {

    @GET("discover/movie")
    fun getMoviesList(
        @Query("api_key") api: String,
        @Query("sort_by") sort: String,
        @Query("page") page: Int
    ): Call<JsonObject>

    @GET("movie/{id}")
    fun getMovieDetails(
        @Path("id") id: Int,
        @Query("api_key") api: String
    ): Call<JsonObject>

}

