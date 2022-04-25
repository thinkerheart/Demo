package com.example.demo.data.network

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class DemoApiClient {

    companion object {
        const val baseUrl = "https://newsapi.org/v2/"
        const val apiKey = "8c03fcc6ea0f4a63a64716041861fa1c"
        const val connectTimeoutSecond = 15L
        const val readTimeoutSecond = 30L
        const val writeTimeoutSecond = 30L
    }

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val okBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
    private val adapterBuilder: Retrofit.Builder = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(moshi))

    init {
        createDefaultAdapter()
    }

    fun <S> createService(serviceClass: Class<S>): S {
        return adapterBuilder
            .client(okBuilder.build())
            .build()
            .create(serviceClass)
    }

    private fun createDefaultAdapter() {
        val logger = HttpLoggingInterceptor.Logger { message -> Log.d(this.javaClass.simpleName, message) }
        val interceptor = HttpLoggingInterceptor(logger)
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        okBuilder.addInterceptor(interceptor)
        okBuilder.addInterceptor(getApiKeyInterceptor())

        okBuilder.connectTimeout(connectTimeoutSecond, TimeUnit.SECONDS)
        okBuilder.readTimeout(readTimeoutSecond, TimeUnit.SECONDS)
        okBuilder.writeTimeout(writeTimeoutSecond, TimeUnit.SECONDS)
    }

    private fun getApiKeyInterceptor() = Interceptor { chain ->
        var request = chain.request()
        val url = request.url.newBuilder().addQueryParameter("apiKey", apiKey).build()
        request = request.newBuilder().url(url).build()
        chain.proceed(request)
    }
}