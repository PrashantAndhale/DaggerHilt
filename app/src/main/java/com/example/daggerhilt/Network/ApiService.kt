package com.example.daggerhilt.Network

import com.example.daggerhilt.Model.GlobalMarketData
import com.example.daggerhilt.Model.MarketStatus
import com.example.daggerhilt.Model.Post
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {
    @GET("posts")
    suspend fun getPost(): List<Post>

    @GET
    suspend fun getGlobalIndicatesListingData(
        @Url url: String,
        @Query("view") view: String,
        @Query("deviceType") deviceType: String
    ): GlobalMarketData

    @GET("marketStatus")
    suspend fun getMarketStatus(): MarketStatus

}