package com.example.trypaging3.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.trypaging3.data.local.entity.MovieEntity

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(data: List<MovieEntity>)

    @Query("SELECT * FROM movieentity")
    fun getAllGame() : PagingSource<Int, MovieEntity>

    @Query("DELETE FROM movieentity")
    suspend fun clearGame()
}