package com.example.trypaging3

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.trypaging3.data.MovieRespository
import com.example.trypaging3.data.local.AppDatabase
import com.example.trypaging3.data.network.api.ApiService
import com.example.trypaging3.ui.ViewModelFactory

object Injection {
    private fun provideGameRespository(context: Context) : MovieRespository {
        return MovieRespository(ApiService.create(), AppDatabase.getInstance(context))
    }

    fun provideViewModelFactory(context: Context) : ViewModelProvider.Factory {
        return ViewModelFactory(provideGameRespository(context))
    }
}