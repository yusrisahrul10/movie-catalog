package com.yusrish.catalogmovie.screen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yusrish.catalogmovie.di.Injection
import com.yusrish.catalogmovie.screen.genre.GenreViewModel
import com.yusrish.catalogmovie.screen.movie.MovieViewModel
import com.yusrish.catalogmovie.screen.moviedetail.MovieDetailViewModel

class ViewModelFactory(private val context: Context) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(GenreViewModel::class.java) -> {
                GenreViewModel(Injection.provideRepository(context)) as T
            }
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> {
                MovieViewModel(Injection.provideRepository(context)) as T
            }
            modelClass.isAssignableFrom(MovieDetailViewModel::class.java) -> {
                MovieDetailViewModel(Injection.provideRepository(context)) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: "+modelClass.name)
        }
    }
}