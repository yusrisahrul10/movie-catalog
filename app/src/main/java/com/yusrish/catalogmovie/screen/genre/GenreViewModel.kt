package com.yusrish.catalogmovie.screen.genre

import androidx.lifecycle.ViewModel
import com.yusrish.catalogmovie.data.DataRepository

class GenreViewModel(private val dataRepository: DataRepository) : ViewModel() {

    suspend fun getGenreForMovie() = dataRepository.getGenreForMovie()
}