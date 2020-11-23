package com.example.trypaging3.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.trypaging3.data.network.api.ApiService
import com.example.trypaging3.data.network.response.GameResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow

private const val NETWORK_PAGE_SIZE = 50

@FlowPreview
@ExperimentalCoroutinesApi
class GameRespository(private val service: ApiService) {

    fun getResultStream() : Flow<PagingData<GameResponse>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                GamePagingSource(
                    service
                )
            }
        ).flow
    }
}