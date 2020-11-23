package com.example.trypaging3.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.trypaging3.data.MovieRespository
import java.lang.IllegalArgumentException

class ViewModelFactory (private val repsitory: MovieRespository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repsitory) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}