package com.yusrish.catalogmovie.di

import android.content.Context
import com.yusrish.catalogmovie.data.DataRepository
import com.yusrish.catalogmovie.data.remote.network.ApiConfig

object Injection {
    fun provideRepository(context: Context): DataRepository {
        val apiService = ApiConfig.getApiService(context)
        return DataRepository(apiService)
    }
}