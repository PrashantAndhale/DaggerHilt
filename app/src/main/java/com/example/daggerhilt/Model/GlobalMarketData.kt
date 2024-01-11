package com.example.daggerhilt.Model

import kotlinx.serialization.Serializable

@Serializable
data class GlobalMarketData(
    val header: List<MarketDataHeader>,
    val dataList: List<MarketDataList>
)

@Serializable
data class MarketDataHeader(
    val name: String,
    val type: String
)

@Serializable
data class MarketDataList(
    val heading: String,
    val data: List<List<String>>
)
