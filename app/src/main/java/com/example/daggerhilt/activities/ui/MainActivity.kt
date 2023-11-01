package com.example.daggerhilt.activities.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.example.daggerhilt.Model.Post
import com.example.daggerhilt.Utils.ApiState
import com.example.daggerhilt.Utils.Utility
import com.example.daggerhilt.ViewModel.MainViewModel
import com.example.daggerhilt.activities.ui.ui.theme.DaggerHiltTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var utility: Utility

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (utility.isInternetConnected()) {
            mainViewModel.getPost()
        }
        getApiResult()
        setContent {
            SetUiContent()
        }
    }

    private fun getApiResult() {
        lifecycleScope.launch {
            mainViewModel._postStateFlow.collect { state ->
                when (state) {
                    is ApiState.Success<*> -> {
                        val data = (state.data as? List<Post>) ?: emptyList()
                        processData(data)
                    }

                    is ApiState.Loading -> mainViewModel._isLoading.value = true
                    else -> {
                        mainViewModel._isLoading.value = false
                        // Handle other states or errors if needed
                    }
                }
            }
        }
    }

    private fun processData(data: List<Post>) {
        mainViewModel._isLoading.value = false
        setContent {
            DaggerHiltTheme {
                SetUiContent(data)
            }
        }
        Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_LONG).show()
    }

    @Composable
    private fun SetUiContent(data: List<Post> = emptyList()) {
        val isLoading by mainViewModel.isLoading.collectAsState()
        Surface {
            Column {
                MyToolbar("Dashboard")
                if (isLoading) {
                    CircularProgressIndicator()
                } else {
                    PostList(data)
                }
            }
        }
    }

    @Composable
    private fun PostList(posts: List<Post>) {
        LazyColumn {
            items(posts) { post ->
                PostItem(post)
            }
        }
    }

    @Composable
    private fun PostItem(post: Post) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
        ) {
            ClickableCard(post = post) { clickedPost ->
                Toast.makeText(this@MainActivity, clickedPost.body, Toast.LENGTH_LONG).show()
            }
        }
    }

    @Composable
    private fun ClickableCard(post: Post, onItemClick: (Post) -> Unit) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    onItemClick(post)
                }
        ) {
            Text(
                text = post.body,
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(16.dp)
            )
        }
    }

    @Composable
    private fun CircularProgressIndicator() {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color.Black)
        }
    }
}

@Composable
fun MyToolbar(title: String) {
    TopAppBar(
        title = {
            Text(
                text = title,
                color = Color.White,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
            )
        },
        backgroundColor = Color.Blue,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier.fillMaxWidth(),
        color = Color.White
    )
}

@Preview(showBackground = false)
@Composable
fun GreetingPreview() {
    DaggerHiltTheme {
        Greeting("Android")
    }
}
