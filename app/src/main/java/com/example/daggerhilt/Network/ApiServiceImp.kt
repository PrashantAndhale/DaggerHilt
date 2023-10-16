package com.example.daggerhilt.Network

import com.example.daggerhilt.Model.Post
import javax.inject.Inject

class ApiServiceImp @Inject constructor(val apiService: ApiService) {
    suspend fun getPost(): List<Post> = apiService.getPost()
}