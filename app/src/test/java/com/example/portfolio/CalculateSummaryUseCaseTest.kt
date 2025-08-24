package com.example.portfolio

import com.example.portfolio.data.model.Holding
import com.example.portfolio.domain.usecase.CalculateSummaryUseCase
import com.example.portfolio.domain.usecase.PortfolioSummary
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class CalculateSummaryUseCaseTest {

    private lateinit var calculateSummaryUseCase: CalculateSummaryUseCase

    @Before
    fun setup() {
        calculateSummaryUseCase = CalculateSummaryUseCase()
    }

    @Test
    fun `invoke should return zeros when holdings list is empty`() {
        val holdings = emptyList<Holding>()
        val expected = PortfolioSummary(
            currentValue = 0.0,
            totalInvestment = 0.0,
            totalPNL = 0.0,
            todaysPNL = 0.0,
            pnlPercentage = 0.0
        )

        val result = calculateSummaryUseCase.invoke(holdings)

        assertEquals(expected, result)
    }

    @Test
    fun `invoke should calculate correct summary for single holding`() {
        val holdings = listOf(
            Holding(symbol = "ABC", quantity = 10, ltp = 50.0, avgPrice = 40.0, close = 45.0)
        )

        val expected = PortfolioSummary(
            currentValue = 500.0,          // 10 * 50
            totalInvestment = 400.0,       // 10 * 40
            totalPNL = 100.0,              // 500 - 400
            todaysPNL = 50.0,              // (50 - 45) * 10
            pnlPercentage = 25.0           // (100 / 400) * 100
        )

        val result = calculateSummaryUseCase.invoke(holdings)

        assertEquals(expected, result)
    }

    @Test
    fun `invoke should calculate correct summary for multiple holdings`() {
        val holdings = listOf(
            Holding(symbol = "ABC", quantity = 10, ltp = 50.0, avgPrice = 40.0, close = 45.0),
            Holding(symbol = "XYZ", quantity = 5, ltp = 100.0, avgPrice = 120.0, close = 110.0)
        )

        val currentValue = 500.0 + 500.0 // 10*50 + 5*100
        val totalInvestment = 400.0 + 600.0 // 10*40 + 5*120
        val totalPNL = currentValue - totalInvestment // 1000 - 1000 = 0
        val todaysPNL: Double = ((50 - 45)*10 + (100 - 110)*5).toDouble() // 50 - 50 = 0
        val pnlPercentage = 0.0

        val expected = PortfolioSummary(
            currentValue = currentValue,
            totalInvestment = totalInvestment,
            totalPNL = totalPNL,
            todaysPNL = todaysPNL,
            pnlPercentage = pnlPercentage
        )

        val result = calculateSummaryUseCase.invoke(holdings)

        assertEquals(expected, result)
    }

    @Test
    fun `invoke should handle totalInvestment zero gracefully`() {
        val holdings = listOf(
            Holding(symbol = "ZERO", quantity = 0, ltp = 100.0, avgPrice = 0.0, close = 100.0)
        )

        val expected = PortfolioSummary(
            currentValue = 0.0,
            totalInvestment = 0.0,
            totalPNL = 0.0,
            todaysPNL = 0.0,
            pnlPercentage = 0.0
        )

        val result = calculateSummaryUseCase.invoke(holdings)

        assertEquals(expected, result)
    }
}
