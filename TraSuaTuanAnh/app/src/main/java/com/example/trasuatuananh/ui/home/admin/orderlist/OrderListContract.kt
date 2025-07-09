package com.example.trasuatuananh.ui.home.admin.orderlist

import androidx.lifecycle.LiveData
import com.example.trasuatuananh.api.model.response.BillAdminResponse
import com.example.trasuatuananh.base.AppBehaviorOnServiceError
import com.example.trasuatuananh.base.BasePresenter
import com.example.trasuatuananh.base.BaseView

interface OrderListContract {
    interface View : BaseView, AppBehaviorOnServiceError {

    }

    interface Presenter : BasePresenter {
        fun getListOrderAdmin(status: Int)
        fun listOrder(): LiveData<List<BillAdminResponse>>
    }
}