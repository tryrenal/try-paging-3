package com.example.trypaging3.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id : Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "backdrop_path")
    val backdrop_path : String
)