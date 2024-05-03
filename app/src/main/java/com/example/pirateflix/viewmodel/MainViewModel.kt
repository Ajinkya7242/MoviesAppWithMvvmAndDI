package com.example.pirateflix.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.denzcoskun.imageslider.models.SlideModel
import com.example.pirateflix.model.Top100MoviesModelItem
import com.example.pirateflix.model.TopSeriesModelItem
import com.example.pirateflix.retrofit.RetrofitInstanceImdbMovies
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _images = MutableLiveData<ArrayList<SlideModel>>()
    private var topMoviesLiveData = MutableLiveData<List<Top100MoviesModelItem>>()
    private var topSereisLiveData = MutableLiveData<List<TopSeriesModelItem>>()

   fun getBannerImages(){
       val images=ArrayList<SlideModel>()
       images.add(SlideModel("https://tse3.mm.bing.net/th?id=OIP.DG8Qg1mRIq3FIpFhyluqVgHaLH&pid=Api&P=0&h=220", "The animal population decreased by 58 percent in 42 years."))
       images.add(SlideModel("https://tse3.mm.bing.net/th?id=OIP.DG8Qg1mRIq3FIpFhyluqVgHaLH&pid=Api&P=0&h=220", "Elephants and tigers may become extinct."))
       images.add(SlideModel("https://tse3.mm.bing.net/th?id=OIP.DG8Qg1mRIq3FIpFhyluqVgHaLH&pid=Api&P=0&h=220", "And people do that."))
       _images.value=images

   }

    fun observeImages():LiveData<ArrayList<SlideModel>>{
        return _images
    }


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

    fun observeTopMovies():LiveData<List<Top100MoviesModelItem>>{
        return topMoviesLiveData
    }

    fun observeTopSeries():LiveData<List<TopSeriesModelItem>>{
        return topSereisLiveData
    }
}
