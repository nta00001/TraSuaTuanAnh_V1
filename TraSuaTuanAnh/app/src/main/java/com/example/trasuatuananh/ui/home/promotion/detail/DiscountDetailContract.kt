package com.example.trasuatuananh.ui.home.promotion.detail

import androidx.lifecycle.LiveData
import com.example.trasuatuananh.api.model.response.VoucherResponse
import com.example.trasuatuananh.base.AppBehaviorOnServiceError
import com.example.trasuatuananh.base.BasePresenter
import com.example.trasuatuananh.base.BaseView

interface DiscountDetailContract {
    interface View: BaseView, AppBehaviorOnServiceError {
        fun getStringRes(id: Int): String

    }
    interface Presenter: BasePresenter {

        fun getListVoucher()
        fun listVoucher(): LiveData<List<VoucherResponse>>
        fun coupon(): LiveData<String>

    }
}