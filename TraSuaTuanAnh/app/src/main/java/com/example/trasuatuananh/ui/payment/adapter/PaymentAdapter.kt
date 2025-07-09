package com.example.trasuatuananh.ui.payment.adapter

import com.bumptech.glide.Glide
import com.example.trasuatuananh.R
import com.example.trasuatuananh.api.Config
import com.example.trasuatuananh.api.model.Food
import com.example.trasuatuananh.base.AbsListAdapter
import com.example.trasuatuananh.databinding.ItemMilkTeaPayMentBinding

class PaymentAdapter(
) : AbsListAdapter<Food, ItemMilkTeaPayMentBinding>({ item1, item2 ->
    item1 == item2
}) {

    override val itemLayoutRes: Int = R.layout.item_milk_tea_pay_ment

    override fun onBindData(
        item: Food,
        position: Int,
        viewDataBinding: ItemMilkTeaPayMentBinding
    ) {
        viewDataBinding.apply {

            this.item = item

            Glide.with(ivTea.context)
                .load(Config.urlImage + item.imageUrl)
                .placeholder(R.drawable.ic_null)
                .error(R.drawable.ic_null)
                .into(ivTea)
        }
    }

}