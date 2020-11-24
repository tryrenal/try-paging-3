package com.example.trypaging3.data.api

import com.example.trypaging3.data.api.response.RootResponse
import com.example.trypaging3.utils.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService  {

    @GET("movie/popular")
    suspend fun getPopularMovie(
        @Query("api_key") apiKey : String,
        @Query("page") page: Int
    ) : RootResponse

    companion object{

        fun create(): ApiService {
            val client = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor())
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}