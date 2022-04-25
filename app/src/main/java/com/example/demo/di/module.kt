package com.example.demo.di

import com.example.demo.data.network.DemoApiClient
import com.example.demo.data.network.api.NewsApi
import com.example.demo.repository.NewsRepository
import com.example.demo.screen.HeadlineViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { DemoApiClient() }
    single { (get() as DemoApiClient).createService(NewsApi::class.java) }
    single { NewsRepository(get()) }
    viewModel { HeadlineViewModel(get()) }
}