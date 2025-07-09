package com.example.trasuatuananh.ui.history.detail

import com.example.trasuatuananh.api.model.response.OrderDetailResponse
import com.example.trasuatuananh.base.AppBehaviorOnServiceError
import com.example.trasuatuananh.base.BasePresenter
import com.example.trasuatuananh.base.BaseView
import kotlinx.coroutines.flow.StateFlow

interface HistoryOrderDetailContract {
    interface View : BaseView, AppBehaviorOnServiceError {


    }

    interface Presenter : BasePresenter {
        fun getOrderDetail()
        fun oderDetail():StateFlow<OrderDetailResponse?>
    }
}