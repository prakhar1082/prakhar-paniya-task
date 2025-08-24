package com.example.portfolio.ui.adapter

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.portfolio.R
import com.example.portfolio.data.model.Holding
import com.example.portfolio.databinding.ItemHoldingBinding
import kotlin.math.abs
/**
 * Adapter for displaying a list of [Holding] items in a RecyclerView.
 *
 * This adapter uses [ListAdapter] with a [DiffUtil.ItemCallback] to efficiently update
 * the list when data changes. It binds each [Holding] to its corresponding item view.
 */
class HoldingsAdapter :
    ListAdapter<Holding, HoldingsAdapter.HoldingViewHolder>(Diff) {

    /**
     * A [DiffUtil.ItemCallback] for calculating the difference between two [Holding] items.
     * - Items are considered the same if they have the same `symbol`.
     * - Contents are considered the same if all fields of [Holding] match.
     */
    object Diff : DiffUtil.ItemCallback<Holding>() {
        override fun areItemsTheSame(oldItem: Holding, newItem: Holding) =
            oldItem.symbol == newItem.symbol

        override fun areContentsTheSame(oldItem: Holding, newItem: Holding) =
            oldItem == newItem
    }

    /**
     * Creates a new [HoldingViewHolder] by inflating [ItemHoldingBinding].
     *
     * @param parent The parent view that the new view will be attached to.
     * @param viewType The type of the new view (unused since only one type is used).
     * @return A new [HoldingViewHolder] instance.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoldingViewHolder {
        val binding = ItemHoldingBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HoldingViewHolder(binding)
    }

    /**
     * Binds the [Holding] at the given position to the [HoldingViewHolder].
     *
     * @param holder The [HoldingViewHolder] to bind data into.
     * @param position The position of the [Holding] item in the list.
     */
    override fun onBindViewHolder(holder: HoldingViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    /**
     * ViewHolder responsible for binding a single [Holding] item to the UI.
     *
     * @property binding The [ItemHoldingBinding] associated with this ViewHolder.
     */
    class HoldingViewHolder(private val binding: ItemHoldingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Binds the given [Holding] to the view.
         *
         * - Displays the stock symbol and LTP (last traded price).
         * - Formats the "Net Qty" text with different colors for label and value.
         * - Calculates and displays P&L with appropriate color (green for positive, red for negative).
         *
         * @param item The [Holding] to bind into the item view.
         */
        fun bind(item: Holding) {
            val ctx = binding.root.context

            // Symbol and LTP
            binding.tvHoldingSymbol.text = item.symbol
            binding.tvLTP.text = ctx.getString(R.string.label_ltp, item.ltp)

            val netQtyLabel = ctx.getString(R.string.label_net_qty)
            val netQtyVal = item.quantity.toString()
            val netQtySpannable = SpannableStringBuilder()
                .append(
                    netQtyLabel,
                    ForegroundColorSpan(ContextCompat.getColor(ctx, R.color.gray_label)),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                .append(
                    netQtyVal,
                    ForegroundColorSpan(ContextCompat.getColor(ctx, R.color.black_value)),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            binding.tvNetQty.text = netQtySpannable

            val pnl = (item.ltp - item.avgPrice) * item.quantity
            val pnlLabel = ctx.getString(R.string.label_pnl)
            val amount = ctx.getString(R.string.formatted_amount, abs(pnl))
            val color = if (pnl >= 0) R.color.positive_green else R.color.negative_red
            val sign = if (pnl >= 0) "" else "- "

            val pnlSpannable = SpannableStringBuilder()
                .append(
                    pnlLabel,
                    ForegroundColorSpan(ContextCompat.getColor(ctx, R.color.gray_label)),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                .append(
                    "$sign$amount",
                    ForegroundColorSpan(ContextCompat.getColor(ctx, color)),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            binding.tvPNL.text = pnlSpannable
        }
    }
}
