package com.example.daggerhilt.Model

class demo {
    sealed class Result {
        data class Success(val data: String) : Result()
        data class Error(val message: String) : Result()
        object Loading : Result()
    }

    interface ResultHandler {
        fun handleResult(result: Result)
    }

    class ExampleClass : ResultHandler {
        override fun handleResult(result: Result) {
            TODO("Not yet implemented")
        }

    }

    fun main() {
        val exampleClass = ExampleClass()

        // Example usage
        exampleClass.handleResult(Result.Success("Data received"))
        exampleClass.handleResult(Result.Error("Failed to load data"))
        exampleClass.handleResult(Result.Loading)
    }
}