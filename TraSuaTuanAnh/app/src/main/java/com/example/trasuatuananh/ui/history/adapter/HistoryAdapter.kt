package com.example.trasuatuananh.ui.history.adapter

import androidx.core.content.ContextCompat
import com.example.trasuatuananh.R
import com.example.trasuatuananh.api.model.response.HistoryOrderResponse
import com.example.trasuatuananh.base.AbsListAdapter
import com.example.trasuatuananh.databinding.ItemOrderHistoryBinding

class HistoryAdapter(

    val onItemClickListener: ((String, Int) -> Unit)? = null
) : AbsListAdapter<HistoryOrderResponse, ItemOrderHistoryBinding>({ item1, item2 ->
    item1 == item2
}) {
    override val itemLayoutRes: Int
        get() = R.layout.item_order_history

    override fun onBindData(
        item: HistoryOrderResponse,
        position: Int,
        viewDataBinding: ItemOrderHistoryBinding
    ) {
        viewDataBinding.apply {
            this.item = item
            llItem.setOnClickListener {
                onItemClickListener?.invoke(item.orderId, position)
            }
            when (item.orderStatus) {
                "0" -> {
                    txtStatus.text = txtStatus.context.getString(R.string.status_pending)
                    txtStatus.setTextColor(
                        ContextCompat.getColor(
                            txtStatus.context,
                            R.color.text_brand
                        )
                    )
                }

                "1" -> {
                    txtStatus.text = txtStatus.context.getString(R.string.status_shipping)
                    txtStatus.setTextColor(
                        ContextCompat.getColor(
                            txtStatus.context,
                            R.color.orange_FFA928
                        )
                    )
                }

                "2" -> {
                    txtStatus.text = txtStatus.context.getString(R.string.status_completed)
                    txtStatus.setTextColor(
                        ContextCompat.getColor(
                            txtStatus.context,
                            R.color.black_alpha_text_24
                        )
                    )
                }

                "3" -> {
                    txtStatus.text = txtStatus.context.getString(R.string.status_cancelled)
                    txtStatus.setTextColor(
                        ContextCompat.getColor(
                            txtStatus.context,
                            R.color.red_text_FF4D4D
                        )
                    )
                }

                else -> {
                    txtStatus.text = ""
                }
            }

        }
    }
}