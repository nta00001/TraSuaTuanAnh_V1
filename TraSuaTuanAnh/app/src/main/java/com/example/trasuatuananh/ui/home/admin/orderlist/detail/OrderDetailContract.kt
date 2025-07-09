package com.example.trasuatuananh.ui.home.admin.orderlist.detail

import androidx.lifecycle.LiveData
import com.example.trasuatuananh.api.model.BubbleTea
import com.example.trasuatuananh.base.AppBehaviorOnServiceError
import com.example.trasuatuananh.base.BasePresenter
import com.example.trasuatuananh.base.BaseView

interface OrderDetailContract {
    interface View : BaseView, AppBehaviorOnServiceError {
        fun confirmSuccess(message: String)
        fun getStringRes(id: Int): String
    }

    interface Presenter : BasePresenter {
        fun address(): LiveData<String>
        fun phoneNumber(): LiveData<String>
        fun totalAmount(): LiveData<String>
        fun confirmOrder(id: String)
        fun cancelOrder(id: String)
        fun listFood(): LiveData<List<BubbleTea>>
    }

}