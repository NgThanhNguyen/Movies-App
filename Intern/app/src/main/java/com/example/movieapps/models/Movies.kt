package com.example.movieapps.models

import com.google.gson.annotations.SerializedName

data class Movies(
    @SerializedName(value = "id")
    val id: Long?,

    @SerializedName(value = "title")
    val title: String?,

    @SerializedName(value = "overview")
    val overview: String?,

    @SerializedName(value = "backdrop_path")
    val backdrop_path: String?,

    @SerializedName(value = "poster_path")
    val poster_path: String?,

    @SerializedName(value = "vote_average")
    val vote_average: Float?,

    @SerializedName(value = "release_date")
    val release_date: String?

)