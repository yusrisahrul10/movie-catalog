package com.yusrish.catalogmovie.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.yusrish.catalogmovie.BuildConfig
import com.yusrish.catalogmovie.data.remote.model.genre.GenreResponse
import com.yusrish.catalogmovie.data.remote.model.movie.ResultMovie
import com.yusrish.catalogmovie.data.remote.model.review.ResultReview
import com.yusrish.catalogmovie.data.remote.model.video.VideoResponse
import com.yusrish.catalogmovie.data.remote.network.ApiService
import com.yusrish.catalogmovie.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DataRepository(private val apiService: ApiService) {

    suspend fun getGenreForMovie(): Flow<NetworkResult<GenreResponse>> =
        flow {
            emit(NetworkResult.Loading())
            try {
                val response = apiService.getGenre(generateApiKey())
                emit(NetworkResult.Success(response))
            } catch (e: Exception) {
                emit(NetworkResult.Error(e.message.toString()))
            }
        }

    suspend fun getVideo(movieId: Int): Flow<NetworkResult<VideoResponse>> =
        flow {
            emit(NetworkResult.Loading())
            try {
                val response = apiService.getVideo(movieId, generateApiKey())
                emit(NetworkResult.Success(response))
            } catch (e: Exception) {
                emit(NetworkResult.Error(e.message.toString()))
            }
        }

    fun getMovies(genreId: Int): Flow<PagingData<ResultMovie>> =
        Pager(config = PagingConfig(pageSize = 10), pagingSourceFactory = {
            MoviePagingSource(
                genreId,
                apiService
            )
        }).flow

    fun getReview(movieId: Int): Flow<PagingData<ResultReview>> =
        Pager(config = PagingConfig(pageSize = 10), pagingSourceFactory = {
            ReviewPagingSource(
                movieId,
                apiService
            )
        }).flow


    private fun generateApiKey(): String {
        return BuildConfig.API_KEY
    }
}