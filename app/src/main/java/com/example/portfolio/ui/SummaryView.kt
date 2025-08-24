package com.example.portfolio.ui

import android.view.View
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.example.portfolio.R
import com.example.portfolio.databinding.LayoutSummaryBinding

/**
 * A custom view wrapper around [LayoutSummaryBinding] that manages
 * the expand/collapse behavior of the portfolio summary section.
 *
 * @property binding The view binding for the summary layout.
 */
class SummaryView(private val binding: LayoutSummaryBinding) {

    /**
     * Expands or collapses the summary details section with a smooth transition.
     *
     * When expanded:
     * - Shows `summaryDetail` and `summaryDivider`
     * - Updates the arrow icon to point downward
     *
     * When collapsed:
     * - Hides `summaryDetail` and `summaryDivider`
     * - Updates the arrow icon to point upward
     *
     * @param expanded `true` to expand the summary section, `false` to collapse it.
     */
    fun setExpanded(expanded: Boolean) {
        val transition = AutoTransition().apply { duration = 200 }
        TransitionManager.beginDelayedTransition(binding.root, transition)

        binding.summaryDetail.visibility = if (expanded) View.VISIBLE else View.GONE
        binding.summaryDivider.visibility = if (expanded) View.VISIBLE else View.GONE
        binding.ivArrow.setImageResource(
            if (expanded) R.drawable.ic_arrrow_down else R.drawable.ic_arrrow_up
        )
    }
}
