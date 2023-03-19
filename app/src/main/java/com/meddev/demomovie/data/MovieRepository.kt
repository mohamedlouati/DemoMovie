package com.meddev.demomovie.data

/**
 *Mohamed
 */
class MovieRepository {

    fun getMovies(page: Int) = RetrofitInstance.API.MOVIES_API.getMoviesList(API_KEY, "popularity.desc", page)

    fun getMovieDetails(id: Int) = RetrofitInstance.API.MOVIES_API.getMovieDetails(id, API_KEY)
}