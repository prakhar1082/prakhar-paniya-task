package com.example.portfolio.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.portfolio.R
import com.example.portfolio.databinding.ActivityMainBinding
import com.example.portfolio.databinding.LayoutSummaryBinding
import com.example.portfolio.ui.adapter.HoldingsAdapter
import com.example.portfolio.ui.state.HoldingsUiState
import dagger.hilt.android.AndroidEntryPoint

/**
 * [MainActivity] is the entry point of the Portfolio app.
 *
 * Responsibilities:
 * - Hosts the main UI including the summary section and holdings list.
 * - Observes [PortfolioViewModel] for updates on holdings and summary.
 * - Handles expand/collapse behavior of the summary section.
 * - Displays error and loading states.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var summaryBinding: LayoutSummaryBinding
    private lateinit var summaryView: SummaryView

    private val viewModel: PortfolioViewModel by viewModels()
    private val adapter = HoldingsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        summaryBinding = binding.layoutSummary
        summaryView = SummaryView(summaryBinding)

        // Setup RecyclerView
        binding.rvHoldings.layoutManager = LinearLayoutManager(this)
        binding.rvHoldings.adapter = adapter

        // Toggle expand/collapse on summary header click
        summaryBinding.summaryHeader.setOnClickListener {
            viewModel.toggleSummaryExpanded()
        }

        // Retry button click
        binding.btnRetry.setOnClickListener {
            binding.errorLayout.visibility = View.GONE
            viewModel.fetchHoldings()
        }

        observeViewModel()
        viewModel.fetchHoldings()
    }

    private fun observeViewModel() {
        viewModel.uiState.observe(this) { state ->
            when (state) {
                is HoldingsUiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.errorLayout.visibility = View.GONE
                }

                is HoldingsUiState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.errorLayout.visibility = View.GONE

                    adapter.submitList(state.holdings)

                    // Update summary values
                    summaryBinding.tvCurrentValue.text =
                        getString(R.string.currency_format, state.summary.currentValue)
                    summaryBinding.tvTotalInvestment.text =
                        getString(R.string.currency_format, state.summary.totalInvestment)

                    summaryBinding.tvTodaysPNL.text =
                        getString(R.string.currency_format, state.summary.todaysPNL)
                    val todaysColor =
                        if (state.summary.todaysPNL >= 0) R.color.positive_green else R.color.negative_red
                    summaryBinding.tvTodaysPNL.setTextColor(getColor(todaysColor))

                    summaryBinding.tvTotalPNL.text =
                        getString(
                            R.string.pnl_with_percentage,
                            state.summary.totalPNL,
                            state.summary.pnlPercentage
                        )
                    val totalColor =
                        if (state.summary.totalPNL >= 0) R.color.positive_green else R.color.negative_red
                    summaryBinding.tvTotalPNL.setTextColor(getColor(totalColor))
                }

                is HoldingsUiState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.errorLayout.visibility = View.VISIBLE
                    binding.tvError.text = getString(R.string.retry_again)

                    Toast.makeText(
                        this,
                        "Sorry, please try again after sometime",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        // Observe expand/collapse of summary view
        viewModel.summaryExpanded.observe(this) { expanded ->
            summaryView.setExpanded(expanded)
        }
    }
}
