package com.example.trypaging3.data.network.api

import com.example.trypaging3.data.network.response.RootResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService  {

    @GET("movie/popular")
    suspend fun getPopularMovie(
        @Query("api_key") apiKey : String? = "9764540cc75307bf937adec56fb19821",
        @Query("page") page: Int
    ) : RootResponse

    companion object{
        private const val BASE_URL = "https://api.themoviedb.org/3/"

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