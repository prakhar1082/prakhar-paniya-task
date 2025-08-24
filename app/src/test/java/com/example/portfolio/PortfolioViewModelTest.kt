package com.example.portfolio

import androidx.lifecycle.Observer
import com.example.portfolio.data.model.Holding
import com.example.portfolio.domain.usecase.CalculateSummaryUseCase
import com.example.portfolio.domain.usecase.GetHoldingsUseCase
import com.example.portfolio.domain.usecase.PortfolioSummary
import com.example.portfolio.ui.PortfolioViewModel
import com.example.portfolio.ui.state.HoldingsUiState
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class PortfolioViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: PortfolioViewModel
    private val getHoldingsUseCase: GetHoldingsUseCase = mockk()
    private val calculateSummaryUseCase: CalculateSummaryUseCase = mockk()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = PortfolioViewModel(getHoldingsUseCase, calculateSummaryUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `toggleSummaryExpanded should invert current value`() {
        val initial = viewModel.summaryExpanded.value
        viewModel.toggleSummaryExpanded()
        assertEquals(!(initial ?: false), viewModel.summaryExpanded.value)
        viewModel.toggleSummaryExpanded()
        assertEquals(initial ?: false, viewModel.summaryExpanded.value)
    }

    @Test
    fun `fetchHoldings should emit Success state when use cases succeed`() = runTest {
        val holdings = listOf(Holding("ABC", 10, 50.0, 40.0, 45.0))
        val summary = mockk<PortfolioSummary>()

        coEvery { getHoldingsUseCase() } returns holdings
        every { calculateSummaryUseCase(holdings) } returns summary

        val observer = mockk<Observer<HoldingsUiState>>(relaxed = true)
        viewModel.uiState.observeForever(observer)

        viewModel.fetchHoldings()
        testScheduler.advanceUntilIdle()

        verifySequence {
            observer.onChanged(HoldingsUiState.Loading)
            observer.onChanged(HoldingsUiState.Success(holdings, summary))
        }
    }

    @Test
    fun `fetchHoldings should emit Error state when getHoldingsUseCase throws exception`() = runTest {
        val exception = Exception("Network error")
        coEvery { getHoldingsUseCase() } throws exception

        val observer = mockk<Observer<HoldingsUiState>>(relaxed = true)
        viewModel.uiState.observeForever(observer)

        viewModel.fetchHoldings()
        testScheduler.advanceUntilIdle()

        verifySequence {
            observer.onChanged(HoldingsUiState.Loading)
            observer.onChanged(
                HoldingsUiState.Error("Network error")
            )
        }
    }
}
