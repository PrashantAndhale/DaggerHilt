package com.example.daggerhilt.Utils

sealed class ApiState<out T> {
    object Loading : ApiState<Nothing>()
    data class Failure(val msg: Throwable) : ApiState<Nothing>()
    data class Success<T>(val data: T) : ApiState<T>()
    object Empty : ApiState<Nothing>()
}

sealed class Api<T> {
    object Loading : Api<Nothing>()
    data class Fail<T>(val throwable: Throwable) : Api<T>()
    data class Success<T>(val response: T) : Api<T>()
}