package com.example.trasuatuananh.ui.home.bestselling.adapter

import com.bumptech.glide.Glide
import com.example.trasuatuananh.R
import com.example.trasuatuananh.api.Config
import com.example.trasuatuananh.api.model.Food
import com.example.trasuatuananh.databinding.ItemBestSellingBinding
import com.example.trasuatuananh.common.adapter.BaseRecyclerViewAdapter

class BestSellingAdapter(
    val onClickListener: ((Food, Int) -> Unit)? = null
) : BaseRecyclerViewAdapter<Food, ItemBestSellingBinding>() {

    override val itemLayoutRes: Int = R.layout.item_best_selling

    override fun onBindData(
        item: Food,
        position: Int,
        viewDataBinding: ItemBestSellingBinding
    ) {
        viewDataBinding.apply {
            Glide.with(ivBestSelling.context)
                .load(Config.urlImage + item.imageUrl)
                .placeholder(R.drawable.ic_null)
                .error(R.drawable.ic_null)
                .into(ivBestSelling)

            txtName.text = item.foodName
        }
    }
}