package com.example.daggerhilt.activities.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.example.daggerhilt.Model.Post
import com.example.daggerhilt.R
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
        getApiResult();
        setContent {
            SetUiContent()
        }
    }

    private fun getApiResult() {
        lifecycleScope.launch {
            mainViewModel._postStateFlow.collect {
                when (it) {
                    is ApiState.Success<Any> -> {
                        val data = it.data as List<Post>
                        for (item in data) {
                            Log.d("Main", "onCreate: " + item.body)
                        }
                        setContent {
                            DaggerHiltTheme {
                                MyToolbar("Dashboard")
                                PostList(data)
                            }
                        }
                        mainViewModel._isLoading.value = false
                        Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_LONG).show()
                    }

                    is ApiState.Empty -> {

                    }

                    is ApiState.Loading -> {
                        mainViewModel._isLoading.value = true
                    }

                    else -> {

                    }
                }
            }
        }
    }

    @Composable
    fun PostList(posts: List<Post>) {
        Surface(
            modifier = Modifier.fillMaxSize(), color = Color.White
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MyToolbar("Dashboard")
               // ImageViewExample()

                 LazyColumn {
                     items(posts) { post ->
                         PostItem(post)
                     }
                 }
            }
        }
    }

    @Composable
    fun ImageViewExample() {
        // Load an image from a resource (you can also load from other sources)
        val painter = painterResource(id = R.drawable.demo)

        // Display the image with content scale and modifier
        Image(
            painter = painter,
            contentDescription = null, // Provide a content description if needed for accessibility
            modifier = Modifier
                .size(200.dp, 200.dp) // Set the size of the image
                .padding(16.dp), // Add padding if desired
            contentScale = ContentScale.Crop, // Adjust the content scale as needed
        )
    }

    @Composable
    fun PostItem(post: Post) {
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
    fun ClickableCard(post: Post, onItemClick: (Post) -> Unit) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    onItemClick(post)
                }, // Make the Card clickable
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
        val isLoading by mainViewModel.isLoading.collectAsState()
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.Black)
            }
        }
    }

    @Composable
    fun PostItem(post: Int) {

    }

    @Composable
    private fun SetUiContent() {
        DaggerHiltTheme {
            Column {
                MyToolbar("Dashboard")
                CircularProgressIndicator()
            }

        }
    }
}

@Composable
fun MyToolbar(title: String) {
    TopAppBar(
        title = {
            Text(
                text = title, color = Color.White,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp), // Center-align the text
            )

        }, backgroundColor = Color.Blue, // Change the background color as needed
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier.fillMaxWidth(),
        color = Color.White,

        )
}

@Preview(showBackground = false)
@Composable
fun GreetingPreview() {
    DaggerHiltTheme {
        Greeting("Android")
    }
}