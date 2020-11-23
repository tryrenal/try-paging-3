package com.example.trypaging3.ui

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.trypaging3.data.GameRespository
import com.example.trypaging3.data.network.response.MovieResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
@FlowPreview
class MainViewModel (private val repository: GameRespository) : ViewModel(){

    private var currentMovieResult : Flow<PagingData<MovieResponse>>? = null

    fun gameData() : Flow<PagingData<MovieResponse>> {
        val lastResult = currentMovieResult
        if (lastResult != null){
            return lastResult
        }
        val newResult : Flow<PagingData<MovieResponse>> = repository.getResultStream().cachedIn(viewModelScope)
        currentMovieResult = newResult
        return newResult
    }
}