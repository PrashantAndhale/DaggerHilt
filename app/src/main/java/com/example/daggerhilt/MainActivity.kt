package com.example.daggerhilt

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.daggerhilt.R.id.progressBardemo
import com.example.daggerhilt.Utils.Utility
import com.example.daggerhilt.ViewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.observers.DisposableObserver
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
        val progressBar = findViewById<ProgressBar>(progressBardemo)/* if (utility.isInternetConnected())
             mainViewModel.getPost()
         else Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show()
         lifecycleScope.launch {
             mainViewModel._postStateFlow.collect {
                 when (it) {
                     is ApiState.Success<Any> -> {
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
         }*/

        val observable: Observable<Int> = Observable.just(1, 2, 3)

        val observer = object : DisposableObserver<Int>() {
            override fun onNext(value: Int) {
                // This function is called when a new value is emitted
                println("Received: $value")
            }

            override fun onError(e: Throwable) {
                // This function is called if there's an error in the Observable
                println("Error: ${e.message}")
            }

            override fun onComplete() {
                // This function is called when the Observable completes emitting values
                println("Observable completed")
            }
        }

        observable.subscribe(observer)

        button.setOnClickWithMessage("Button Click") {

        }

    }

    fun Button.setOnClickWithMessage(msg: String, onClick: () -> Unit) {
        this.setOnClickListener {

        }
    }
}

open class ApiState<T> {
    object Loading : ApiState<Nothing>()
    data class Success<T>(val response: T) : ApiState<T>()
    data class Error<T>(val response: Throwable) : ApiState<T>()
}