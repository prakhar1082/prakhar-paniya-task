package com.example.portfolio.data.repository

import com.example.portfolio.data.api.PortfolioApi
import com.example.portfolio.data.model.Holding


class PortfolioRepository(private val api: PortfolioApi) {
    suspend fun fetchHoldings(): List<Holding> {
        return api.getHoldings().data.userHolding
    }
}

