package com.example.portfolio.ui.state

import com.example.portfolio.data.model.Holding
import com.example.portfolio.domain.usecase.PortfolioSummary

sealed class HoldingsUiState {
    object Loading : HoldingsUiState()
    data class Success(
        val holdings: List<Holding>,
        val summary: PortfolioSummary
    ) : HoldingsUiState()
    data class Error(val message: String) : HoldingsUiState()
}
