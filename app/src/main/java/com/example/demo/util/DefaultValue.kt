package com.example.demo.util

fun String?.defaultIfNull(): String {
    return this ?: ""
}