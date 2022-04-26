package com.example.demo.repository

import com.example.demo.Response
import com.example.demo.data.network.api.NewsApi
import com.example.demo.model.HeadlinesResponse
import com.example.demo.util.defaultIfNull
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class NewsRepository(private val newsApi: NewsApi) {

    suspend fun getHeadlines(countryCode: String, category: String): Flow<Response<HeadlinesResponse>> {
        return flow {
            emit(Response.Success(newsApi.getHeadlines(countryCode, category)) as Response<HeadlinesResponse>)
        }.catch {
            emit(Response.Error(it.localizedMessage.defaultIfNull()))
        }
    }
}