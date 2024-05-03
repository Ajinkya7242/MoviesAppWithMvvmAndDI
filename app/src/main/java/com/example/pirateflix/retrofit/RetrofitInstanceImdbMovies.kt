package com.example.pirateflix.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstanceImdbMovies {

    private const val BASE_URL = "https://imdb-top-100-movies.p.rapidapi.com/"

    val api: ImdbTopMoviesApi by lazy {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("X-RapidAPI-Key", "be422ee9femshb5a1601ea01e3d1p14098cjsn316c90c31e30")
                    .addHeader("X-RapidAPI-Host", "imdb-top-100-movies.p.rapidapi.com")
                    .build()
                chain.proceed(request)
            }
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ImdbTopMoviesApi::class.java)
    }
}
