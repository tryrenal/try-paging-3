package com.example.trypaging3.data

import androidx.paging.PagingSource
import com.example.trypaging3.data.network.api.ApiService
import com.example.trypaging3.data.network.response.MovieResponse
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class MoviePagingSource (
    private val service : ApiService
) : PagingSource<Int, MovieResponse>(){
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResponse> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = service.getPopularMovie(page = position)
            val repos = response.result
            LoadResult.Page(
                data = repos,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position,
                nextKey = if (repos.isEmpty()) null else position + 1
            )
        } catch (e: IOException){
            return LoadResult.Error(e)
        } catch (e: HttpException){
            return LoadResult.Error(e)
        }
    }
}