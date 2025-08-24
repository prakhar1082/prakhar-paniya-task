package com.example.portfolio.ui

import androidx.lifecycle.*
import com.example.portfolio.domain.usecase.CalculateSummaryUseCase
import com.example.portfolio.domain.usecase.GetHoldingsUseCase
import com.example.portfolio.ui.state.HoldingsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import javax.inject.Inject
/**
 * ViewModel responsible for managing and providing portfolio holdings data and summary state.
 *
 * This ViewModel interacts with the domain layer via use cases:
 * - [GetHoldingsUseCase] to fetch the list of holdings
 * - [CalculateSummaryUseCase] to compute the portfolio summary.
 *
 * It exposes two main LiveData objects:
 * - [uiState]: Represents the current UI state (Loading, Success, or Error).
 * - [summaryExpanded]: Controls the expand/collapse state of the summary section.
 *
 * Network requests are wrapped with a 5-second timeout to prevent indefinite loading.
 *
 * @property getHoldingsUseCase Use case to fetch holdings data from repository layer.
 * @property calculateSummaryUseCase Use case to calculate summary based on holdings data.
 */
@HiltViewModel
class PortfolioViewModel @Inject constructor(
    private val getHoldingsUseCase: GetHoldingsUseCase,
    private val calculateSummaryUseCase: CalculateSummaryUseCase
) : ViewModel() {

    /**
     * Backing property for UI state.
     * Can be one of [HoldingsUiState.Loading], [HoldingsUiState.Success], or [HoldingsUiState.Error].
     */
    private val _uiState = MutableLiveData<HoldingsUiState>()
    val uiState: LiveData<HoldingsUiState> = _uiState

    /**
     * Backing property for expanded/collapsed state of the summary section.
     * Default is collapsed (`false`).
     */
    private val _summaryExpanded = MutableLiveData(false)
    val summaryExpanded: LiveData<Boolean> = _summaryExpanded

    /**
     * Toggles the current state of [summaryExpanded].
     * - If currently expanded, collapses the summary.
     * - If currently collapsed, expands the summary.
     */
    fun toggleSummaryExpanded() {
        _summaryExpanded.value = !(_summaryExpanded.value ?: false)
    }

    /**
     * Fetches holdings and updates [uiState] accordingly.
     *
     * - Emits [HoldingsUiState.Loading] initially.
     * - Uses [getHoldingsUseCase] to fetch holdings.
     * - Uses [calculateSummaryUseCase] to compute summary.
     * - Emits [HoldingsUiState.Success] if successful within 5 seconds.
     * - Emits [HoldingsUiState.Error] if request times out or any exception occurs.
     *
     * Timeout is set to **5 seconds** using [withTimeout] to prevent indefinite loading.
     */
    fun fetchHoldings() {
        _uiState.value = HoldingsUiState.Loading
        viewModelScope.launch {
            try {
                // ⏳ Add timeout of 5 seconds
                withTimeout(5000) {
                    val holdings = getHoldingsUseCase()
                    val summary = calculateSummaryUseCase(holdings)
                    _uiState.value = HoldingsUiState.Success(holdings, summary)
                }
            } catch (e: TimeoutCancellationException) {
                _uiState.value = HoldingsUiState.Error("Request timed out. Please try again.")
            } catch (e: Exception) {
                _uiState.value = HoldingsUiState.Error(
                    e.localizedMessage ?: "Something went wrong"
                )
            }
        }
    }
}
