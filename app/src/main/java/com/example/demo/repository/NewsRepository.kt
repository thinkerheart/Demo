package com.example.demo.repository

import com.example.demo.Response
import com.example.demo.data.network.api.NewsApi
import com.example.demo.model.HeadlinesResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NewsRepository(private val newsApi: NewsApi) {

    suspend fun getHeadlines(countryCode: String, category: String): Flow<Response<HeadlinesResponse>> {
        return flow {
            emit(Response.Success(newsApi.getHeadlines(countryCode, category)))
        }
    }
}