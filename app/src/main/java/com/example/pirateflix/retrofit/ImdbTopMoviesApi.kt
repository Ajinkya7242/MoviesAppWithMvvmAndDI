package com.example.pirateflix.retrofit

import com.example.pirateflix.model.MoviesDetailsModel
import com.example.pirateflix.model.SeriesDetailsModel
import com.example.pirateflix.model.Top100MoviesModelItem
import com.example.pirateflix.model.TopSeriesModelItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ImdbTopMoviesApi {

    @GET("/")
    fun getTopMovies(): Call<List<Top100MoviesModelItem>>


    @GET("/series/")
    fun getTopSeries(): Call<List<TopSeriesModelItem>>


    @GET("/{endpoint}")
    fun getMovieDetails(@Path("endpoint") endpoint: String): Call<MoviesDetailsModel>

    @GET("/series/{endpoint}")
    fun getSereisDetails(@Path("endpoint") endpoint: String): Call<SeriesDetailsModel>
}