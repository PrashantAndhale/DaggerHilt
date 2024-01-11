package com.example.daggerhilt.Repositories

import com.example.daggerhilt.Model.GlobalMarketData
import com.example.daggerhilt.Model.Post
import com.example.daggerhilt.Network.ApiServiceImp
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainRespository @Inject constructor(val apiServiceImp: ApiServiceImp) {
    fun getPost(): Flow<List<Post>> = flow {
        emit(apiServiceImp.getPost())
    }.flowOn(Dispatchers.IO)

    fun getGlobalIndicatesListingData(
        url: String,
        view: String,
        deviceType: String
    ): Flow<GlobalMarketData> = flow {
        emit(apiServiceImp.getGlobalIndicatesListingData(url, view, deviceType))
    }.flowOn(Dispatchers.IO)
}