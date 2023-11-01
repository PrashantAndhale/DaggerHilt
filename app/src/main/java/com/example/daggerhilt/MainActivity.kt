package com.example.daggerhilt

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.daggerhilt.Model.Post
import com.example.daggerhilt.R.id.progressBardemo
import com.example.daggerhilt.Utils.ApiState
import com.example.daggerhilt.Utils.Utility
import com.example.daggerhilt.ViewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.observers.DisposableObserver
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity(
) : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    lateinit var button: Button

    @Inject
    lateinit var utility: Utility

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val progressBar = findViewById<ProgressBar>(progressBardemo)
        if (utility.isInternetConnected())
             mainViewModel.getPost()
         else Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show()
         lifecycleScope.launch {
             mainViewModel._postStateFlow.collect {
                 when (it) {
                     is ApiState.Success<*> -> {
                         val data = it.data as List<Post>
                         for (item in data) {
                             Log.d("Main", "onCreate: " + item.body)
                         }
                         progressBar.visibility = View.GONE
                         Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_LONG).show()
                     }

                     is ApiState.Empty -> {

                     }

                     is ApiState.Loading -> {
                         progressBar.visibility = View.VISIBLE
                     }

                     else -> {

                     }
                 }
             }
         }


    }

 }