package com.example.trypaging3.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.trypaging3.data.local.entity.MovieRemoteKeys

@Dao
interface MovieRemoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(data: List<MovieRemoteKeys>)

    @Query("SELECT * FROM movieremotekeys WHERE id = :id")
    suspend fun movieKeysById(id: Int) : MovieRemoteKeys?

    @Query("DELETE FROM movieremotekeys")
    suspend fun clearMovieKeys()
}