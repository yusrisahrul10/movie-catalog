package com.yusrish.catalogmovie.data.remote.model.video

import com.google.gson.annotations.SerializedName

data class VideoResponse(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("results")
    val resultVideo: List<ResultVideo>
)
