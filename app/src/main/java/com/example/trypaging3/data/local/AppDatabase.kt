package com.example.trypaging3.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.trypaging3.data.local.dao.MovieDao
import com.example.trypaging3.data.local.dao.MovieRemoteDao
import com.example.trypaging3.data.local.entity.MovieEntity
import com.example.trypaging3.data.local.entity.MovieRemoteKeys

@Database(
    entities = [MovieEntity::class, MovieRemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase(){
    abstract fun movieDao() : MovieDao
    abstract fun movieKeyDao() : MovieRemoteDao

    companion object{
        @Volatile
        private var INSTANCE : AppDatabase? = null

        fun getInstance(context: Context) : AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it}
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
            AppDatabase::class.java, "Game.db")
                .build()
    }
}