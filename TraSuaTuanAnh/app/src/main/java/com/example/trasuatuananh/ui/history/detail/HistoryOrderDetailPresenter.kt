package com.example.trasuatuananh.ui.history.detail

import com.example.trasuatuananh.api.model.response.OrderDetailResponse
import com.example.trasuatuananh.api.repository.order.OrderRepository
import com.example.trasuatuananh.base.CommonPresenter
import kotlinx.coroutines.flow.MutableStateFlow

class HistoryOrderDetailPresenter(
    private val view: HistoryOrderDetailContract.View,
    private val orderRepository: OrderRepository,
    private val orderId: String
) : CommonPresenter(view, view), HistoryOrderDetailContract.Presenter {
    private val _orderDetail = MutableStateFlow<OrderDetailResponse?>(null)
    override fun getOrderDetail() {
        baseCallApi(orderRepository.getOrderDetail(orderId), onSuccess = { response ->
            _orderDetail.value = response?.data

        }, onError = {

        })

    }

    override fun oderDetail() = _orderDetail
}