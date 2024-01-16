package com.example.daggerhilt.Network

import com.example.daggerhilt.Model.GlobalMarketData
import com.example.daggerhilt.Model.MarketStatus
import com.example.daggerhilt.Model.Post
import javax.inject.Inject

class ApiServiceImp @Inject constructor(val apiService: ApiService) {
    suspend fun getPost(): List<Post> = apiService.getPost()
    suspend fun getGlobalIndicatesListingData(
        url: String,
        view: String,
        deviceType: String
    ): GlobalMarketData = apiService.getGlobalIndicatesListingData(url, view, deviceType)

    suspend fun getMarketStatus(
        url: String,
    ): MarketStatus = apiService.getMarketStatus()
}