package com.example.trypaging3.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.trypaging3.data.local.AppDatabase
import com.example.trypaging3.data.local.entity.MovieEntity
import com.example.trypaging3.data.local.entity.MovieRemoteKeys
import com.example.trypaging3.data.network.api.ApiService
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException

private const val STARTING_PAGE_INDEX = 1

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator (
    private val service: ApiService,
    private val database: AppDatabase
) : RemoteMediator<Int, MovieEntity>(){
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        val page = when(val pageKeyData = getKeyPageData(loadType, state)){
            is MediatorResult.Success -> {
                return pageKeyData
            }
            else -> {
                pageKeyData as Int
            }
        }

        try {
            val apiResponse = service.getPopularMovie(page = page)
            val repos = apiResponse.result
            val endOfPaginationReached = repos.isEmpty()
            database.withTransaction {
                if (loadType == LoadType.REFRESH){
                    database.movieKeyDao().clearMovieKeys()
                    database.movieDao().clearGame()
                }
                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = repos.map {
                    MovieRemoteKeys(id = it.id, nextKey = nextKey, prevKey = prevKey)
                }
                database.movieKeyDao().insertAll(keys)
                val entity = repos.map {
                    MovieEntity(id = it.id, title = it.title, backdrop_path = it.backdrop_path)
                }
                database.movieDao().insertAll(entity)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        }catch (e: IOException){
            return MediatorResult.Error(e)
        }catch (e: HttpException){
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, MovieEntity>): Any? {
        return when(loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                    ?: throw InvalidObjectException("Remote key should not be null for $loadType")
                remoteKeys.nextKey
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                    ?: throw InvalidObjectException("Remote key and the prevKey should not be null")
                remoteKeys.prevKey ?: return MediatorResult.Success(endOfPaginationReached = true)
                remoteKeys.prevKey
            }
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, MovieEntity>): MovieRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { data ->
                database.movieKeyDao().movieKeysById(data.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, MovieEntity>) : MovieRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { data ->
                database.movieKeyDao().movieKeysById(data.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, MovieEntity>
    ) : MovieRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.movieKeyDao().movieKeysById(id)
            }
        }
    }


}