package com.example.demo.data.network.api

import com.example.demo.model.HeadlinesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("top-headlines")
    suspend fun getHeadlines(
        @Query("country") country: String,
        @Query("category") category: String
    ): HeadlinesResponse?
}