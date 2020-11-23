package com.example.trypaging3.ui

import androidx.lifecycle.*
import com.example.trypaging3.data.GameRespository
import com.example.trypaging3.data.api.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@FlowPreview
class MainViewModel (private val repository: GameRespository) : ViewModel(){

    val gameResult : LiveData<ApiResponse> = liveData {
        val data = repository.getResultStream().asLiveData(Dispatchers.Main)
        emitSource(data)
    }

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int){
        if (visibleItemCount + lastVisibleItemPosition >= totalItemCount){
            viewModelScope.launch {
                repository.requestMore()
            }
        }
    }
}