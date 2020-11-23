package com.example.trypaging3.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.trypaging3.data.local.AppDatabase
import com.example.trypaging3.data.local.entity.MovieEntity
import com.example.trypaging3.data.network.api.ApiService
import com.example.trypaging3.data.network.response.MovieResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow

private const val NETWORK_PAGE_SIZE = 50

@FlowPreview
@ExperimentalCoroutinesApi
class MovieRespository(private val service: ApiService, private val database: AppDatabase) {

    fun getResultStream() : Flow<PagingData<MovieEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = MovieRemoteMediator(
              service, database
            ),
            pagingSourceFactory = { database.movieDao().getAllGame() }
        ).flow
    }
}