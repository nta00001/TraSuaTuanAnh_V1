package com.example.trasuatuananh.ui.home.admin.revenue.adapter

import com.example.trasuatuananh.R
import com.example.trasuatuananh.api.model.response.RevenueResponse
import com.example.trasuatuananh.databinding.ItemRevenueBinding
import com.example.trasuatuananh.common.adapter.BaseRecyclerViewAdapter

class RevenueAdapter : BaseRecyclerViewAdapter<RevenueResponse, ItemRevenueBinding>() {
    override val itemLayoutRes: Int
        get() = R.layout.item_revenue

    override fun onBindData(
        item: RevenueResponse,
        position: Int,
        viewDataBinding: ItemRevenueBinding
    ) {
        viewDataBinding.apply {
            this.item = item
        }
    }
}