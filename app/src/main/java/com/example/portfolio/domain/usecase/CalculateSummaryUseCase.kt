package com.example.portfolio.domain.usecase
/**
 * Represents a summary of a user's investment portfolio.
 *
 * @property currentValue The total market value of the holdings (sum of `ltp * quantity`).
 * @property totalInvestment The total invested amount (sum of `avgPrice * quantity`).
 * @property totalPNL The overall profit or loss (difference between [currentValue] and [totalInvestment]).
 * @property todaysPNL The profit or loss for the current day (sum of `(ltp - close) * quantity`).
 * @property pnlPercentage The percentage profit or loss relative to [totalInvestment].
 */
data class PortfolioSummary(
    val currentValue: Double,
    val totalInvestment: Double,
    val totalPNL: Double,
    val todaysPNL: Double,
    val pnlPercentage: Double
)

/**
 * Use case responsible for calculating a [PortfolioSummary] from a list of holdings.
 */
class CalculateSummaryUseCase {

    /**
     * Calculates the [PortfolioSummary] for the given list of holdings.
     *
     * @param holdings List of [Holding] items containing investment details.
     * @return A [PortfolioSummary] with aggregated portfolio metrics.
     */
    operator fun invoke(
        holdings: List<com.example.portfolio.data.model.Holding>
    ): PortfolioSummary {
        val currentValue = holdings.sumOf { it.ltp * it.quantity }
        val totalInvestment = holdings.sumOf { it.avgPrice * it.quantity }
        val totalPNL = currentValue - totalInvestment
        val todaysPNL = holdings.sumOf { (it.ltp - it.close) * it.quantity }
        val pnlPercentage = if (totalInvestment != 0.0) (totalPNL / totalInvestment) * 100 else 0.0

        return PortfolioSummary(
            currentValue = currentValue,
            totalInvestment = totalInvestment,
            totalPNL = totalPNL,
            todaysPNL = todaysPNL,
            pnlPercentage = pnlPercentage
        )
    }
}
