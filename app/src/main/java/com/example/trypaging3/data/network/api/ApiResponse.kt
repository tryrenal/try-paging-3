package com.example.trypaging3.data.network.api

import com.example.trypaging3.data.network.response.MovieResponse

sealed class ApiResponse {
    data class Success(val data: List<MovieResponse>) : ApiResponse()
    data class Error(val error: Exception) : ApiResponse()
}