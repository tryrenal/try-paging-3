package com.example.trypaging3

import androidx.lifecycle.ViewModelProvider
import com.example.trypaging3.data.MovieRespository
import com.example.trypaging3.data.api.ApiService
import com.example.trypaging3.ui.ViewModelFactory

object Injection {
    private fun provideGameRespository() : MovieRespository {
        return MovieRespository(ApiService.create())
    }

    fun provideViewModelFactory() : ViewModelProvider.Factory {
        return ViewModelFactory(provideGameRespository())
    }
}