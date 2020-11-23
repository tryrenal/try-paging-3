package com.example.trypaging3

import androidx.lifecycle.ViewModelProvider
import com.example.trypaging3.data.GameRespository
import com.example.trypaging3.data.api.ApiService
import com.example.trypaging3.ui.ViewModelFactory

object Injection {
    private fun provideGameRespository() : GameRespository {
        return GameRespository(ApiService.create())
    }

    fun provideViewModelFactory() : ViewModelProvider.Factory {
        return ViewModelFactory(provideGameRespository())
    }
}