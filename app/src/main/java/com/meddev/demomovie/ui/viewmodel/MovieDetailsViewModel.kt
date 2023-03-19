package com.meddev.demomovie.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.meddev.demomovie.data.MovieRepository
import com.meddev.demomovie.models.MovieDetails
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 *Mohamed
 */
class MovieDetailsViewModel: ViewModel() {
    private val repository = MovieRepository()
    val movieDetails = MutableLiveData<MovieDetails>()
    val errorMessage = MutableLiveData<String>()

    fun getMovieDetails(id: Int){
        val response = repository.getMovieDetails(id)
        response.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                val movie = Gson().fromJson(response.body()!!.toString(),MovieDetails::class.java)
                movieDetails.postValue(movie)
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                errorMessage.postValue(t.message)
            }

        })
    }

}