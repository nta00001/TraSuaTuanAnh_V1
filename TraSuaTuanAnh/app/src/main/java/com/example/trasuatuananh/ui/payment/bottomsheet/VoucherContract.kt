package com.example.trasuatuananh.ui.payment.bottomsheet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.trasuatuananh.api.model.Selectable
import com.example.trasuatuananh.api.model.response.VoucherResponse
import com.example.trasuatuananh.base.AppBehaviorOnServiceError
import com.example.trasuatuananh.base.BasePresenter
import com.example.trasuatuananh.base.BaseView

interface VoucherContract {

    interface View : BaseView, AppBehaviorOnServiceError {

    }

    interface Presenter : BasePresenter {
        fun changeInterest(pos: Int)
        fun getPrevPosition(): Int
        fun listCoupon(): MutableLiveData<MutableList<Selectable<VoucherResponse>>>
        fun voucher(): LiveData<VoucherResponse>
        fun setVoucher(value: VoucherResponse)
    }
}