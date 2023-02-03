package com.yusrish.catalogmovie.screen.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.yusrish.catalogmovie.data.DataRepository
import com.yusrish.catalogmovie.data.remote.model.movie.ResultMovie

class MovieViewModel(private val dataRepository: DataRepository) : ViewModel()  {
    fun getMovie(genreId: Int) : LiveData<PagingData<ResultMovie>> =
        dataRepository.getMovies(genreId).cachedIn(viewModelScope).asLiveData()
}