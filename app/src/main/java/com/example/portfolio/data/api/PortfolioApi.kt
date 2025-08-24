package com.example.portfolio.data.api

import com.example.portfolio.data.model.HoldingsResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

/**
 * Retrofit service interface that defines the API endpoints
 * for fetching portfolio data such as user holdings.
 *
 * This API uses Moshi as the JSON converter and is created via Retrofit.
 */
interface PortfolioApi {

    /**
     * Fetches the list of user holdings from the portfolio API.
     *
     * @return [HoldingsResponse] containing the user's holdings data
     */
    @GET("/")
    suspend fun getHoldings(): HoldingsResponse

    companion object {
        fun create(): PortfolioApi {
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://35dee773a9ec441e9f38d5fc249406ce.api.mockbin.io/")
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
            return retrofit.create(PortfolioApi::class.java)
        }
    }
}
