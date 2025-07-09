package com.example.trasuatuananh.ui.history

import com.example.trasuatuananh.api.model.response.HistoryOrderResponse
import com.example.trasuatuananh.api.repository.order.OrderRepository
import com.example.trasuatuananh.base.CommonPresenter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class HistoryOrderPresenter(
    private val view: HistoryOrderContract.View,
    private val orderRepository: OrderRepository

) : CommonPresenter(view, view), HistoryOrderContract.Presenter {
    private val _orderList = MutableStateFlow<List<HistoryOrderResponse>>(emptyList())
    private val _selectedStatus = MutableStateFlow(setOf("0", "1"))

    private val _filteredList = combine(_orderList, _selectedStatus) { list, status ->
        list.filter { it.orderStatus in status }
            .sortedByDescending { it.orderDate }
    }.stateIn(coroutineScope, SharingStarted.Lazily, emptyList())

    override fun getHistoryOrder() {
        baseCallApi(orderRepository.getHistoryOrder(),
            onSuccess = { response ->
                _orderList.value = response?.data ?: emptyList()
                setOrderStatus(setOf("0", "1"))
            },
            onError = {

            })
    }

    override fun orderList() = _orderList
    override fun filteredList() = _filteredList

    override fun setOrderStatus(status: Set<String>) {
        _selectedStatus.value = status
    }

}