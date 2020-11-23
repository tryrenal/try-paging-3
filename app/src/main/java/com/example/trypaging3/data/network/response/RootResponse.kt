package com.example.trypaging3.data.network.response

import com.google.gson.annotations.SerializedName

data class RootResponse(
    @field:SerializedName("page")
    val page: Int? = null,
    @field:SerializedName("results")
    val result: List<GameResponse> = emptyList()
)