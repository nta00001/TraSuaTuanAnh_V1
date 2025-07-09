package com.example.trasuatuananh.ui.payment.qr

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import com.example.trasuatuananh.api.model.Food
import com.example.trasuatuananh.base.AppBehaviorOnServiceError
import com.example.trasuatuananh.base.BasePresenter
import com.example.trasuatuananh.base.BaseView

interface PaymentQrContract {
    interface View : BaseView, AppBehaviorOnServiceError {
        fun showDiaLog(message: String)
        fun setImageQr(bitmap: Bitmap)
        fun showError(message: String)
        fun getStringRes(id: Int): String
        fun sendListFoodBroadcast(list: List<Food>)

    }

    interface Presenter : BasePresenter {
        fun getQrCode()
        fun price(): LiveData<String>
        fun message(): LiveData<String>
        fun submitOrder()
    }
}