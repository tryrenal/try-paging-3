package com.example.trypaging3.ui

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.trypaging3.data.MovieRespository
import com.example.trypaging3.data.local.entity.MovieEntity
import com.example.trypaging3.data.network.response.MovieResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
@FlowPreview
class MainViewModel (private val repository: MovieRespository) : ViewModel(){

    private var currentMovieResult : Flow<PagingData<MovieEntity>>? = null

    fun gameData() : Flow<PagingData<MovieEntity>> {
        val lastResult = currentMovieResult
        if (lastResult != null){
            return lastResult
        }
        val newResult : Flow<PagingData<MovieEntity>> = repository.getResultStream().cachedIn(viewModelScope)
        currentMovieResult = newResult
        return newResult
    }
}