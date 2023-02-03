package com.yusrish.catalogmovie.data.remote.network

import com.yusrish.catalogmovie.data.remote.model.genre.GenreResponse
import com.yusrish.catalogmovie.data.remote.model.movie.MovieResponse
import com.yusrish.catalogmovie.data.remote.model.review.ReviewResponse
import com.yusrish.catalogmovie.data.remote.model.video.VideoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("genre/movie/list")
    suspend fun getGenre(
        @Query("api_key") apiKey: String
    ) : GenreResponse

    @GET("movie/popular")
    suspend fun getMovie(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ) : MovieResponse

    @GET("movie/{movie_id}/reviews")
    suspend fun getReview(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ) : ReviewResponse

    @GET("movie/{movie_id}/videos")
    suspend fun getVideo(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ) : VideoResponse
}