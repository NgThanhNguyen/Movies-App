package com.example.movieapps.services

import com.example.movieapps.models.Movie_Respond
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Query

interface MoviesAPI {

    @GET("movie/now_playing")
    fun getMovies(
        @Query("api_key") apiKey: String = "d8adc83adc59e52667575cd36e4062e5",
        @Query("page") page: Int

    ): Call<Movie_Respond>

}