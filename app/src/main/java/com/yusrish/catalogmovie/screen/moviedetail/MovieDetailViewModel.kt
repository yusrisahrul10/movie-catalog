package com.yusrish.catalogmovie.screen.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.yusrish.catalogmovie.data.DataRepository
import com.yusrish.catalogmovie.data.remote.model.review.ResultReview

class MovieDetailViewModel(private val dataRepository: DataRepository) : ViewModel()  {
    fun getReview(movieId: Int) : LiveData<PagingData<ResultReview>> =
        dataRepository.getReview(movieId).cachedIn(viewModelScope).asLiveData()

    suspend fun getVideo(movieId: Int) = dataRepository.getVideo(movieId)
}