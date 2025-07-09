package com.example.trasuatuananh.ui.home.listfood.adapter

import com.bumptech.glide.Glide
import com.example.trasuatuananh.R
import com.example.trasuatuananh.api.Config
import com.example.trasuatuananh.api.model.Food
import com.example.trasuatuananh.base.AbsListAdapter
import com.example.trasuatuananh.databinding.ItemListFoodBinding

class ListFoodAdapter(
    val onItemClickListener: ((Food, Int) -> Unit)? = null,
    val onAddClickListener: ((Food, Int) -> Unit)? = null,
    val onSubClickListener: ((Food, Int) -> Unit)? = null
) : AbsListAdapter<Food, ItemListFoodBinding>({ item1, item2 ->
    item1 == item2
}) {

    override val itemLayoutRes: Int = R.layout.item_list_food

    override fun onBindData(
        item: Food,
        position: Int,
        viewDataBinding: ItemListFoodBinding
    ) {
        viewDataBinding.apply {

            this.item = item

            Glide.with(ivTea.context)
                .load(Config.urlImage + item.imageUrl)
                .placeholder(R.drawable.ic_null)
                .error(R.drawable.ic_null)
                .into(ivTea)
            ctlItem.setOnClickListener {
                onItemClickListener?.invoke(item, position)
            }

            btnAdd.setOnClickListener {
                onAddClickListener?.invoke(item, position)
            }

            btnSub.setOnClickListener {
                onSubClickListener?.invoke(item, position)
            }
        }
    }

}