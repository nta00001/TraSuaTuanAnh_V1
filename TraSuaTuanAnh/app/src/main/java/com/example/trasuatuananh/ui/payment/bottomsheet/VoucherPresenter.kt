package com.example.trasuatuananh.ui.payment.bottomsheet

import androidx.lifecycle.MutableLiveData
import com.example.trasuatuananh.api.model.Selectable
import com.example.trasuatuananh.api.model.response.VoucherResponse
import com.example.trasuatuananh.base.CommonPresenter

class VoucherPresenter(
    private val view: VoucherContract.View,
    list: List<VoucherResponse>?
) : CommonPresenter(), VoucherContract.Presenter {
    private val _coupon = MutableLiveData<VoucherResponse>()
    private val _listCoupon = MutableLiveData<MutableList<Selectable<VoucherResponse>>>().apply {
        value = list?.mapIndexed { index, item ->
            Selectable(item).apply {
                selected = index == -1
            }
        }?.toMutableList()
    }

    private var _prevPosition = 0
    private var _selectedPosition = 0

    override fun changeInterest(pos: Int) {
        _prevPosition = _selectedPosition
        _selectedPosition = pos
        _listCoupon.value?.forEachIndexed { index, interest ->
            interest.selected = pos == index
        }
    }

    override fun getPrevPosition() = _prevPosition
    override fun listCoupon() = _listCoupon
    override fun voucher() = _coupon

    override fun setVoucher(value: VoucherResponse) {
        _coupon.value = value
    }


}