package com.yusrish.catalogmovie.data.remote.model.review

import com.google.gson.annotations.SerializedName

data class ReviewResponse(
    @field:SerializedName("page")
    val page: Int,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("results")
    val resultReview: List<ResultReview>
)
