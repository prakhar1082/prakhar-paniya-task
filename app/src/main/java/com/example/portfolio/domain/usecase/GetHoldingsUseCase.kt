package com.example.portfolio.domain.usecase

import com.example.portfolio.data.model.Holding
import com.example.portfolio.data.repository.PortfolioRepository

class GetHoldingsUseCase(private val repository: PortfolioRepository) {
    suspend operator fun invoke(): List<Holding> = repository.fetchHoldings()
}
