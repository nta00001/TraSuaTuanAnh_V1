package com.example.trasuatuananh.ui.payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.trasuatuananh.api.model.Food
import com.example.trasuatuananh.api.model.Item
import com.example.trasuatuananh.api.model.response.VoucherResponse
import com.example.trasuatuananh.base.AppBehaviorOnServiceError
import com.example.trasuatuananh.base.BasePresenter
import com.example.trasuatuananh.base.BaseView

interface PaymentContract {
    interface View : BaseView, AppBehaviorOnServiceError {
        fun showDiaLog(message: String)
        fun getStringRes(id: Int): String
        fun sendListFoodBroadcast(list: List<Food>)

    }

    interface Presenter : BasePresenter {
        fun listFood(): LiveData<List<Food>>
        fun listItem(): LiveData<List<Item>>
        fun isQrSelected(): MutableLiveData<Boolean>
        fun setIsQrSelected(value: Boolean)
        fun removeBubbleTea(position: Int)
        fun submitOrder()
        fun setListItem(listFood: List<Food>)
        fun address(): LiveData<String>
        fun phoneNumber(): LiveData<String>
        fun voucher(): LiveData<VoucherResponse>
        fun setVoucher(value: VoucherResponse)
        fun placeOrder(): LiveData<String>
        fun setPlaceOrder(value: String)
        fun info(): LiveData<String>
        fun totalAmount(): LiveData<String>
        fun shippingFee(): LiveData<String>
        fun discountFee(): LiveData<String>
        fun setDiscountFee(value: String)
        fun totalItemAmount(): LiveData<String>
        fun setTotalItemAmount()
        fun getListVoucher()
        fun listVoucher(): LiveData<List<VoucherResponse>>

    }
}