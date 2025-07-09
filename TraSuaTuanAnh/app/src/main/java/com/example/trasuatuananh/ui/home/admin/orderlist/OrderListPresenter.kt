package com.example.trasuatuananh.ui.home.admin.orderlist

import androidx.lifecycle.MutableLiveData
import com.example.trasuatuananh.api.model.response.BillAdminResponse
import com.example.trasuatuananh.api.repository.food.FoodRepository
import com.example.trasuatuananh.base.CommonPresenter

class OrderListPresenter(
    private val view: OrderListContract.View,
    private val foodRepository: FoodRepository
) : CommonPresenter(view, view), OrderListContract.Presenter {
    private val _listOrder = MutableLiveData<List<BillAdminResponse>>()
    override fun getListOrderAdmin(status: Int) {
        baseCallApi(
            foodRepository.getListOrderAdmin(
                null,
                status
            ),
            onSuccess = {
                _listOrder.value = it

            },
            onError = {

            })
    }

    override fun listOrder() = _listOrder

}