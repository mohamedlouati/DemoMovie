package com.meddev.demomovie.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.meddev.demomovie.data.MovieRepository
import com.meddev.demomovie.models.MoviesItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 *Mohamed
 */
class MovieListViewModel: ViewModel() {
    private val repository = MovieRepository()
    val moviesList = MutableLiveData<Array<MoviesItem>>()
    val errorMessage = MutableLiveData<String>()

    fun getAllMovies(page: Int){
        val response = repository.getMovies(page)
        response.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                val list = Gson().fromJson(response.body()!!.getAsJsonArray("results").toString(),Array<MoviesItem>::class.java)
                moviesList.postValue(list)
            }
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

}