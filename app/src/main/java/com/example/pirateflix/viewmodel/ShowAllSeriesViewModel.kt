package com.example.pirateflix.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pirateflix.model.TopSeriesModelItem
import com.example.pirateflix.retrofit.RetrofitInstanceImdbMovies
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShowAllSeriesViewModel:ViewModel() {

    private var topSereisLiveData = MutableLiveData<List<TopSeriesModelItem>>()


    fun getTopSeries() {
        RetrofitInstanceImdbMovies.api.getTopSeries()
            .enqueue(object : Callback<List<TopSeriesModelItem>> {
                override fun onResponse(
                    call: Call<List<TopSeriesModelItem>>,
                    response: Response<List<TopSeriesModelItem>>
                ) {
                    if (response.body() != null) {

                        topSereisLiveData.value = response.body()!!
                        Log.d("TopSeriesApiFromViewModel", "Response-${response.body()}")

                    }
                }

                override fun onFailure(call: Call<List<TopSeriesModelItem>>, t: Throwable) {
                    Log.d("TopSeriesApiFromViewModel", "Error-${t.message}")
                }

            })
    }


    fun observeTopSeries(): LiveData<List<TopSeriesModelItem>> {
        return topSereisLiveData
    }
}