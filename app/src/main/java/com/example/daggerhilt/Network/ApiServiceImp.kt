package com.example.daggerhilt.Network

import com.example.daggerhilt.Model.GlobalMarketData
import com.example.daggerhilt.Model.Post
import com.google.gson.JsonObject
import javax.inject.Inject

class ApiServiceImp @Inject constructor(val apiService: ApiService) {
    suspend fun getPost(): List<Post> = apiService.getPost()
    suspend fun getGlobalIndicatesListingData(
        url: String,
        view: String,
        deviceType: String
    ): GlobalMarketData = apiService.getGlobalIndicatesListingData(url, view, deviceType)
}