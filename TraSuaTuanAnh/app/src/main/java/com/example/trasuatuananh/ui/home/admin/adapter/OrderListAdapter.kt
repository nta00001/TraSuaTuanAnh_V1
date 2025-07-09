package com.example.trasuatuananh.ui.home.admin.adapter

import com.bumptech.glide.Glide
import com.example.trasuatuananh.R
import com.example.trasuatuananh.api.Config
import com.example.trasuatuananh.api.model.response.BillAdminResponse
import com.example.trasuatuananh.base.AbsListAdapter
import com.example.trasuatuananh.databinding.ItemOrderListBinding

class OrderListAdapter(
    val onClickListener: ((BillAdminResponse, Int) -> Unit)? = null
) : AbsListAdapter<BillAdminResponse, ItemOrderListBinding>({ item1, item2 ->
    item1 == item2
}) {
    override val itemLayoutRes: Int
        get() = R.layout.item_order_list

    override fun onBindData(
        item: BillAdminResponse,
        position: Int,
        viewDataBinding: ItemOrderListBinding
    ) {
        viewDataBinding.apply {
            this.item = item
            Glide.with(ivMon.context)
                .load(item.items.firstOrNull()?.monAn?.id?.let { Config.getImageUrl(it) })
                .placeholder(R.drawable.ic_null)
                .error(R.drawable.ic_null)
                .into(ivMon)
            llItem.setOnClickListener {
                onClickListener?.invoke(item, position)
            }
//            txtQuantity.text =
//                item?.items?.map { item ->
//                    BubbleTea(
//                        id = item.monAn.id,
//                        nameTea = item.monAn.tenMon,
//                        price = item.monAn.gia.toString(),
//                        description = item.monAn.moTa,
//                        ingredientType = IngredientType(
//                            quantity = item.soLuong.toString(),
//                            type = item.size
//                        ),
//                    )
//                }?.let { StringUtils.calculateTotalPrice(it) }
        }


    }
}