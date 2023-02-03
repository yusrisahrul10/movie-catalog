package com.yusrish.catalogmovie.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yusrish.catalogmovie.BuildConfig
import com.yusrish.catalogmovie.data.remote.model.movie.ResultMovie
import com.yusrish.catalogmovie.data.remote.network.ApiService
import kotlinx.coroutines.delay

class MoviePagingSource(private val genreId: Int, private val apiService: ApiService) : PagingSource<Int, ResultMovie>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, ResultMovie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultMovie> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getMovie(generateApiKey(), page = page).resultMovie
            delay(500)

            Log.d("CHECK RESPONSE DATA", page.toString() + responseData.toString())
            LoadResult.Page(
                data = responseData.filter {
                    it.genreIds.contains(genreId)
                },
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (responseData.isEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    private fun generateApiKey(): String {
        return BuildConfig.API_KEY
    }
}