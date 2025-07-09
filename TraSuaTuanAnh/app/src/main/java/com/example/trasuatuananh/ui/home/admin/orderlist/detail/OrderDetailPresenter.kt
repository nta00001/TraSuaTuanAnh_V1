package com.example.trasuatuananh.ui.home.admin.orderlist.detail

import androidx.lifecycle.MutableLiveData
import com.example.trasuatuananh.R
import com.example.trasuatuananh.api.model.BubbleTea
import com.example.trasuatuananh.api.model.IngredientType
import com.example.trasuatuananh.api.model.response.BillAdminResponse
import com.example.trasuatuananh.api.repository.food.FoodRepository
import com.example.trasuatuananh.base.CommonPresenter

class OrderDetailPresenter(
    private val view: OrderDetailContract.View,
    private val foodRepository: FoodRepository,
    order: BillAdminResponse?
) : CommonPresenter(view, view),
    OrderDetailContract.Presenter {

    private val _address = MutableLiveData<String>(order?.address ?: "")
    private val _phoneNumber = MutableLiveData<String>(order?.sdtNgNhan ?: "")
    override fun address() = _address

    override fun phoneNumber() = _phoneNumber

    override fun totalAmount() = _totalAmount
    private val _listFood = MutableLiveData<List<BubbleTea>>().apply {
        value = order?.items?.map { item ->
            BubbleTea(
                id = item.monAn.id,
                nameTea = item.monAn.tenMon,
                price = item.monAn.gia.toString(),
                description = item.monAn.moTa,
                ingredientType = IngredientType(
                    quantity = item.soLuong.toString(),
                    type = item.size
                ),
            )
        }
    }

    private val _totalAmount = MutableLiveData<String>().apply {
//        value = _listFood.value?.let { StringUtils.calculateTotalPrice(it) }
    }



    override fun confirmOrder(id: String) {
    baseCallApi(foodRepository.confirmOrder(id), onSuccess = {
        view.confirmSuccess(
            view.getStringRes(
                R.string.order_status_confirmed
            )
        )

    },
        onError = {

        })
}

override fun cancelOrder(id: String) {
    baseCallApi(foodRepository.cancelOrder(id), onSuccess = {
        view.confirmSuccess(
            view.getStringRes(R.string.order_status_canceled)
        )
    },
        onError = {

        })
}

override fun listFood() = _listFood
}