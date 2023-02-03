package com.yusrish.catalogmovie.data.remote.model.movie

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @field:SerializedName("page")
    val page: Int,

    @field:SerializedName("results")
    val resultMovie: List<ResultMovie>
)
