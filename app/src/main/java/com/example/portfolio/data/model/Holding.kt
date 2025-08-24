package com.example.portfolio.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HoldingsResponse(
    val data: HoldingsData
)

@JsonClass(generateAdapter = true)
data class HoldingsData(
    val userHolding: List<Holding>
)

@JsonClass(generateAdapter = true)
data class Holding(
    val symbol: String,
    val quantity: Int,
    val ltp: Double,
    val avgPrice: Double,
    val close: Double
)
