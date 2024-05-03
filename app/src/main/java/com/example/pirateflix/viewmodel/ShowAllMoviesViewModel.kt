package com.example.pirateflix.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pirateflix.model.Top100MoviesModelItem
import com.example.pirateflix.retrofit.RetrofitInstanceImdbMovies
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShowAllMoviesViewModel : ViewModel() {

    private var topMoviesLiveData = MutableLiveData<List<Top100MoviesModelItem>>()
    fun getTopMovies() {
        RetrofitInstanceImdbMovies.api.getTopMovies()
            .enqueue(object : Callback<List<Top100MoviesModelItem>> {
                override fun onResponse(
                    call: Call<List<Top100MoviesModelItem>>,
                    response: Response<List<Top100MoviesModelItem>>
                ) {
                    if (response.body() != null) {

                        topMoviesLiveData.value = response.body()!!
                        Log.d("TopMoviesApiFromViewModel", "Response-${response.body()}")

                    }
                }

                override fun onFailure(call: Call<List<Top100MoviesModelItem>>, t: Throwable) {
                    Log.d("TopMoviesApiFromViewModel", "Error-${t.message}")
                }

            })
    }

    fun observeTopMovies(): LiveData<List<Top100MoviesModelItem>> {
        return topMoviesLiveData
    }

}