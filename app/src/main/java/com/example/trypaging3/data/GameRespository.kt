package com.example.trypaging3.data

import android.util.Log
import com.example.trypaging3.data.api.ApiResponse
import com.example.trypaging3.data.api.ApiService
import com.example.trypaging3.data.api.response.GameResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import retrofit2.HttpException
import java.io.IOException

private const val MOVIE_STARTING_PAGE = 1

@FlowPreview
@ExperimentalCoroutinesApi
class GameRespository(private val service: ApiService) {
    private val inMemoryCache = mutableListOf<GameResponse>()
    private val apiResponse = ConflatedBroadcastChannel<ApiResponse>()

    private var lastRequestedPage =
        MOVIE_STARTING_PAGE
    private var isRequestInProgress = false

    suspend fun getResultStream() : Flow<ApiResponse> {
        lastRequestedPage = 1
        inMemoryCache.clear()
        requestAndSaveData()
        return apiResponse.asFlow()
    }

    suspend fun requestMore() {
        if (isRequestInProgress) return
        val successful = requestAndSaveData()
        if (successful) {
            lastRequestedPage++
        }
    }

    suspend fun retry() {
        if (isRequestInProgress) return
        requestAndSaveData()
    }

    private suspend fun requestAndSaveData() : Boolean {
        isRequestInProgress = true
        var successful = false
        try {
            val response = service.getPopularMovie("9764540cc75307bf937adec56fb19821", lastRequestedPage)
            Log.i("dataMovie", response.result.toString())
            val repos = response.result
            inMemoryCache.addAll(repos)
            apiResponse.offer(ApiResponse.Success(repos))
            successful = true
        }catch (e: IOException){
            apiResponse.offer(ApiResponse.Error(e))
        } catch (e: HttpException){
            apiResponse.offer(ApiResponse.Error(e))
        }
        isRequestInProgress = false
        return successful
    }
}