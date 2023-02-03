package com.yusrish.catalogmovie.data.remote.model.review

import com.google.gson.annotations.SerializedName

data class ResultReview(
    @field:SerializedName("author")
    val author: String,

    @field:SerializedName("author_details")
    val authorDetails: AuthorDetails,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("content")
    val content: String,

    @field:SerializedName("created_at")
    val createdAt: String,

    @field:SerializedName("updated_at")
    val updatedAt: String,

    @field:SerializedName("url")
    val url: String,
)

data class AuthorDetails(
    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("username")
    val username: String,

    @field:SerializedName("avatar_path")
    val avatarPath: String,

    @field:SerializedName("rating")
    val rating: Int? = null,
)
