package com.example.portfolio.di


import com.example.portfolio.data.api.PortfolioApi
import com.example.portfolio.data.repository.PortfolioRepository
import com.example.portfolio.domain.usecase.CalculateSummaryUseCase
import com.example.portfolio.domain.usecase.GetHoldingsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
/**
 * Dagger-Hilt module that provides application-level dependencies
 * related to portfolio data, repositories, and use cases.
 *
 * All dependencies are installed in the [SingletonComponent],
 * meaning they live as long as the application lifecycle.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * Provides a singleton instance of [PortfolioApi] for making
     * network requests to fetch portfolio-related data.
     *
     * @return A configured [PortfolioApi] instance.
     */
    @Provides
    @Singleton
    fun providePortfolioApi(): PortfolioApi = PortfolioApi.create()

    /**
     * Provides a singleton instance of [PortfolioRepository] which acts
     * as the single source of truth for portfolio data by interacting
     * with the [PortfolioApi].
     *
     * @param api The API service for fetching portfolio data.
     * @return A [PortfolioRepository] instance.
     */
    @Provides
    @Singleton
    fun provideRepository(api: PortfolioApi): PortfolioRepository =
        PortfolioRepository(api)

    /**
     * Provides a singleton instance of [GetHoldingsUseCase] for retrieving
     * the user's holdings. It internally delegates work to [PortfolioRepository].
     *
     * @param repository The repository handling portfolio data operations.
     * @return A [GetHoldingsUseCase] instance.
     */
    @Provides
    @Singleton
    fun provideGetHoldingsUseCase(repository: PortfolioRepository): GetHoldingsUseCase =
        GetHoldingsUseCase(repository)

    /**
     * Provides a singleton instance of [CalculateSummaryUseCase] for calculating
     * the portfolio summary (current value, investment, P&L, etc.).
     *
     * Since it does not depend on external services, it can be created directly.
     *
     * @return A [CalculateSummaryUseCase] instance.
     */
    @Provides
    @Singleton
    fun provideCalculateSummaryUseCase(): CalculateSummaryUseCase =
        CalculateSummaryUseCase()
}
