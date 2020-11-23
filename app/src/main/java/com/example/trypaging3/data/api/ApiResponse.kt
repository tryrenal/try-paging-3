package com.example.trypaging3.data.api

import com.example.trypaging3.data.api.response.GameResponse

sealed class ApiResponse {
    data class Success(val data: List<GameResponse>) : ApiResponse()
    data class Error(val error: Exception) : ApiResponse()
}