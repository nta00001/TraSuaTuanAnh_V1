package com.example.trasuatuananh.ui.home.location.adapter

import com.example.trasuatuananh.R
import com.example.trasuatuananh.databinding.ItemSelectAddressBinding
import com.example.trasuatuananh.common.adapter.BaseRecyclerViewAdapter

class SelectAddressAdapter(
    val onClickListener: ((String, Int) -> Unit)? = null
) : BaseRecyclerViewAdapter<String, ItemSelectAddressBinding>() {
    override val itemLayoutRes: Int
        get() = R.layout.item_select_address

    override fun onBindData(
        item: String,
        position: Int,
        viewDataBinding: ItemSelectAddressBinding
    ) {
        viewDataBinding.apply {
            tvAddress.text = item
            tvAddress.setOnClickListener {
                onClickListener?.invoke(item, position)
            }
        }
    }
}