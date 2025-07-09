package com.example.trasuatuananh.ui.home.admin.revenue

import androidx.lifecycle.LiveData
import com.example.trasuatuananh.api.model.response.RevenueResponse
import com.example.trasuatuananh.base.AppBehaviorOnServiceError
import com.example.trasuatuananh.base.BasePresenter
import com.example.trasuatuananh.base.BaseView

interface RevenueContract {
    interface View : BaseView, AppBehaviorOnServiceError {
        fun getStringRes(id: Int): String
    }

    interface Presenter : BasePresenter {

        fun getRevenueData(daysBefore: Int)
        fun listRevenue(): LiveData<List<RevenueResponse>>
        fun totalAmount(): LiveData<String>
        fun timePeriod(): LiveData<String>
        fun setTimePeriod(value:String)
    }
}