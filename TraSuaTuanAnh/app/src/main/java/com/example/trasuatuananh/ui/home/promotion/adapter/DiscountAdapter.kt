package com.example.trasuatuananh.ui.home.promotion.adapter

import com.example.trasuatuananh.R
import com.example.trasuatuananh.api.model.response.VoucherResponse
import com.example.trasuatuananh.databinding.ItemCouponBinding
import com.example.trasuatuananh.common.adapter.BaseRecyclerViewAdapter

class DiscountAdapter : BaseRecyclerViewAdapter<VoucherResponse, ItemCouponBinding>() {
    override val itemLayoutRes: Int
        get() = R.layout.item_coupon

    override fun onBindData(
        item: VoucherResponse,
        position: Int,
        viewDataBinding: ItemCouponBinding
    ) {
        viewDataBinding.apply {
            this.item = item
        }
    }
}