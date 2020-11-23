package com.example.trypaging3.ui

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.trypaging3.data.GameRespository
import com.example.trypaging3.data.api.ApiResponse
import com.example.trypaging3.data.api.response.GameResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@FlowPreview
class MainViewModel (private val repository: GameRespository) : ViewModel(){

    private var currentGameResult : Flow<PagingData<GameResponse>>? = null

    fun gameData() : Flow<PagingData<GameResponse>> {
        val lastResult = currentGameResult
        if (lastResult != null){
            return lastResult
        }
        val newResult : Flow<PagingData<GameResponse>> = repository.getResultStream().cachedIn(viewModelScope)
        currentGameResult = newResult
        return newResult
    }
}