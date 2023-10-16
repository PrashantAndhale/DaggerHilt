package com.example.daggerhilt.Network

import com.example.daggerhilt.Model.Post
import retrofit2.http.GET

interface ApiService {
    @GET("posts")
    suspend fun getPost(): List<Post>
}