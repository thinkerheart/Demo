package com.example.demo

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import coil.annotation.ExperimentalCoilApi
import com.example.demo.screen.Headline
import com.example.demo.screen.HeadlineViewModel
import org.koin.android.ext.android.inject

@ExperimentalCoilApi
class MainActivity : AppCompatActivity() {
    private val headlineViewModel: HeadlineViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Headline(headlineViewModel)
        }
    }
}