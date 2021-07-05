package com.example.movieapps.services

import com.example.movieapps.models.Movie_Respond
import com.example.movieapps.models.Movies
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MovieRepository {
    private val api: MoviesAPI

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(MoviesAPI::class.java)
    }

    fun getMovies(page: Int = 1, onSuccess: (movies: MutableList<Movies>) -> Unit, onError: () -> Unit) {
        api.getMovies(page = page)
            .enqueue(object : Callback<Movie_Respond> {
                override fun onResponse(
                    call: Call<Movie_Respond>,
                    response: Response<Movie_Respond>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.movies)
                        } else {
                            onError.invoke()
                        }
                    }
                    else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<Movie_Respond>, t: Throwable) {
                    onError.invoke()
                }
            })
    }
}