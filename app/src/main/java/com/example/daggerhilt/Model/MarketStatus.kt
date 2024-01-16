package com.example.daggerhilt.Model

data class MarketStatus(
    val marketcap: Marketcap,
    val indicativenifty: Indicativenifty,
    val marketState: List<MarketStateItem>?
)

data class Indicativenifty(
    val dateTime: String = "",
    val perChange: Double? = null,
    val closingValue: Double? = null,
    val indexName: String = "",
    val change: Double? = null,
    val finalClosingValue: Double? = null,
    val status: String = ""
)


data class MarketStateItem(
    val market: String = "",
    val marketStatusMessage: String = "",
    val percentChange: Double? = null,
    val last: Double? = null,
    val marketStatus: String = "",
    val index: String = "",
    val tradeDate: String = "",
    val variation:Double? = null,
)


data class Marketcap(
    val timeStamp: String = "",
    val marketCapinLACCRRupeesFormatted: String = "",
    val marketCapinCRRupeesFormatted: String = "",
    val marketCapinTRDollars: Double? = null,
    val underlying: String = "",
    val marketCapinLACCRRupees: Double? = null,
    val marketCapinCRRupees: Double? = null,
)


