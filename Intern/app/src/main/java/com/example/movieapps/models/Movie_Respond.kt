package com.example.movieapps.models

import com.google.gson.annotations.SerializedName


data class Movie_Respond(
    @SerializedName(value = "page")
    val page: Int?,

    @SerializedName(value = "total_pages")
    val pages: Int?,

    @SerializedName(value = "results")
    val movies : MutableList<Movies>

)
