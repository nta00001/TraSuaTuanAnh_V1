package com.example.trasuatuananh.ui.history

import com.example.trasuatuananh.api.model.response.HistoryOrderResponse
import com.example.trasuatuananh.base.AppBehaviorOnServiceError
import com.example.trasuatuananh.base.BasePresenter
import com.example.trasuatuananh.base.BaseView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


interface HistoryOrderContract {
    interface View : BaseView, AppBehaviorOnServiceError {


    }

    interface Presenter : BasePresenter {

        fun getHistoryOrder()
        fun orderList(): MutableStateFlow<List<HistoryOrderResponse>>

        fun filteredList(): StateFlow<List<HistoryOrderResponse>>
        fun setOrderStatus(status: Set<String>)


    }
}