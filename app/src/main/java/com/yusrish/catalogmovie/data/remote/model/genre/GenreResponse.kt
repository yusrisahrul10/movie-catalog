package com.yusrish.catalogmovie.data.remote.model.genre

import com.google.gson.annotations.SerializedName

data class GenreResponse(
    @field:SerializedName("genres")
    val genres: List<Genres>
)
