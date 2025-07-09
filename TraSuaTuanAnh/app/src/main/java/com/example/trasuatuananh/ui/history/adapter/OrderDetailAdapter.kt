package com.example.trasuatuananh.ui.history.adapter

import com.bumptech.glide.Glide
import com.example.trasuatuananh.R
import com.example.trasuatuananh.api.Config
import com.example.trasuatuananh.api.model.Food
import com.example.trasuatuananh.common.adapter.BaseRecyclerViewAdapter
import com.example.trasuatuananh.databinding.ItemOrderDetailHistoryBinding

class OrderDetailAdapter : BaseRecyclerViewAdapter<Food, ItemOrderDetailHistoryBinding>() {
    override val itemLayoutRes: Int
        get() = R.layout.item_order_detail_history

    override fun onBindData(
        item: Food,
        position: Int,
        viewDataBinding: ItemOrderDetailHistoryBinding
    ) {
        viewDataBinding.apply {
            this.item = item
            executePendingBindings()
            Glide.with(ivTea.context)
                .load(Config.urlImage + item.imageUrl)
                .placeholder(R.drawable.ic_null)
                .error(R.drawable.ic_null)
                .into(ivTea)
        }
    }
}