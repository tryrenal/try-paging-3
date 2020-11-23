package com.example.trypaging3.data.api.response

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @field:SerializedName("title")
    val title: String,
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("backdrop_path")
    val backdrop_path : String
)