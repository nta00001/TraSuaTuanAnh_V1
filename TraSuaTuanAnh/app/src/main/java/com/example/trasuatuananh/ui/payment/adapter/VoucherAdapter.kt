package com.example.trasuatuananh.ui.payment.adapter

import com.example.trasuatuananh.R
import com.example.trasuatuananh.api.model.Selectable
import com.example.trasuatuananh.api.model.response.VoucherResponse
import com.example.trasuatuananh.base.SelectableAdapter
import com.example.trasuatuananh.databinding.ItemVoucherBinding

class VoucherAdapter(onClickListener: ((Selectable<VoucherResponse>, Int) -> Unit)? = null) :
    SelectableAdapter<VoucherResponse, ItemVoucherBinding>({ item1, item2 ->
        item1 == item2
    }, onClickListener = onClickListener) {
    override val itemLayoutRes: Int
        get() = R.layout.item_voucher

    override fun onBindData(
        item: Selectable<VoucherResponse>,
        position: Int,
        viewDataBinding: ItemVoucherBinding
    ) {
        viewDataBinding.apply {
            this.item = item.data

            cbSelected.isSelected = item.selected == true

        }
    }
}