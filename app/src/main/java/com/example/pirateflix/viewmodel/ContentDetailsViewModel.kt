package com.example.pirateflix.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pirateflix.model.MoviesDetailsModel
import com.example.pirateflix.model.SeriesDetailsModel
import com.example.pirateflix.model.TopSeriesModelItem
import com.example.pirateflix.retrofit.RetrofitInstanceImdbMovies
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContentDetailsViewModel:ViewModel() {

    private var movieDetails = MutableLiveData<MoviesDetailsModel>()
    private var seriesDetails = MutableLiveData<SeriesDetailsModel>()


    fun getMovieDetails(movieId:String) {
        RetrofitInstanceImdbMovies.api.getMovieDetails(movieId)
            .enqueue(object : Callback<MoviesDetailsModel> {
                override fun onResponse(
                    call: Call<MoviesDetailsModel>,
                    response: Response<MoviesDetailsModel>
                ) {
                    if (response.body() != null) {

                        movieDetails.value = response.body()!!
                        Log.d("TopSeriesApiFromViewModel", "Response-${response.body()}")

                    }
                }

                override fun onFailure(call: Call<MoviesDetailsModel>, t: Throwable) {
                    Log.d("TopSeriesApiFromViewModel", "Error-${t.message}")
                }

            })
    }


    fun getSeriesDetails(sereisId: String) {
        RetrofitInstanceImdbMovies.api.getSereisDetails(sereisId)
            .enqueue(object : Callback<SeriesDetailsModel> {
                override fun onResponse(
                    call: Call<SeriesDetailsModel>,
                    response: Response<SeriesDetailsModel>
                ) {
                    if (response.body() != null) {

                        seriesDetails.value = response.body()!!
                        Log.d("TopSeriesApiFromViewModel", "Response-${response.body()}")

                    }
                }

                override fun onFailure(call: Call<SeriesDetailsModel>, t: Throwable) {
                    Log.d("TopSeriesApiFromViewModel", "Error-${t.message}")
                }

            })
    }


    fun observeMovieDetails(): LiveData<MoviesDetailsModel> {
        return movieDetails
    }

    fun observeSeriesDetails(): LiveData<SeriesDetailsModel> {
        return seriesDetails
    }


}