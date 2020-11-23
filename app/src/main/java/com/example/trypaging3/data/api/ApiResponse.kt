package com.example.trypaging3.data.api

import com.example.trypaging3.data.api.response.MovieResponse

sealed class ApiResponse {
    data class Success(val data: List<MovieResponse>) : ApiResponse()
    data class Error(val error: Exception) : ApiResponse()
}