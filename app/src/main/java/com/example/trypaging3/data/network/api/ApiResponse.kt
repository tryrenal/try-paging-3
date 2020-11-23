package com.example.trypaging3.data.network.api

import com.example.trypaging3.data.network.response.GameResponse

sealed class ApiResponse {
    data class Success(val data: List<GameResponse>) : ApiResponse()
    data class Error(val error: Exception) : ApiResponse()
}