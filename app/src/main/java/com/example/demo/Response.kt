package com.example.demo

sealed class Response<T> {
    data class Success<T>(val data: T) : Response<T>()
    data class Error<T>(val errorValue: String) : Response<T>()
}