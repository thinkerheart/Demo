package com.example.demo.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.demo.Response
import com.example.demo.model.Article
import com.example.demo.model.HeadlinesResponse
import com.example.demo.repository.NewsRepository
import com.example.demo.util.defaultIfNull
import com.example.demo.viewmodel.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception

class HeadlineViewModel(private val newsRepository: NewsRepository) : BaseViewModel() {

    val headline = MutableLiveData<HeadlinesResponse>()

    fun getHeadlines(coroutineScope: CoroutineScope, countryCode: String, category: String) {
        coroutineScope.launch {
            try {
                newsRepository.getHeadlines(countryCode, category).collect {
                    if (it is Response.Success) {
                        headline.value = it.data
                    } else if (it is Response.Error) {
                        error.value = it.errorValue
                    }
                }
            } catch (e: Exception) {
                error.value = e.message
            }
        }
    }
}

@ExperimentalCoilApi
@Composable
fun Headline(headlineViewModel: HeadlineViewModel) {
    val headline = headlineViewModel.headline.observeAsState()
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(key1 = true, block = {
        headlineViewModel.getHeadlines(coroutineScope, "us", "business")
    })
    Scaffold { innerPadding ->
        LazyColumn(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .background(Color.LightGray)
        ) {
            items(items = headline.value?.articles ?: emptyList()) {
                HeadlineItem(it)
            }
        }
    }
}

@ExperimentalCoilApi
@Composable
fun HeadlineItem(article: Article) {
    Column(modifier = Modifier
        .padding(horizontal = 0.dp, vertical = 5.dp)
        .background(Color.White)
    ) {
        Image(
            painter = rememberImagePainter(data = article.urlToImage),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )
        Text(
            text = article.title.defaultIfNull(),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 5.dp, vertical = 0.dp)
        )
        Text(
            text = article.description.defaultIfNull(),
            fontSize = 18.sp,
            modifier = Modifier.padding(horizontal = 5.dp, vertical = 5.dp)
        )
    }
}