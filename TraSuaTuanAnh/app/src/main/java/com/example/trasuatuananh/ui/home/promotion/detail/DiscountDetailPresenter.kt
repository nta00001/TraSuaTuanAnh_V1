package com.example.trasuatuananh.ui.home.promotion.detail

import androidx.lifecycle.MutableLiveData
import com.example.trasuatuananh.R
import com.example.trasuatuananh.api.model.response.VoucherResponse
import com.example.trasuatuananh.api.repository.food.FoodRepository
import com.example.trasuatuananh.base.CommonPresenter
import com.example.trasuatuananh.util.Constants
import com.example.trasuatuananh.util.SharedPreferencesUtils

class DiscountDetailPresenter(
    private val view: DiscountDetailContract.View,
    private val foodRepository: FoodRepository
) : CommonPresenter(view, view), DiscountDetailContract.Presenter {

    private val _listVoucher = MutableLiveData<List<VoucherResponse>>()
    private val _coupon = MutableLiveData<String>()
    override fun getListVoucher() {
        val userID = SharedPreferencesUtils.get(Constants.KEY.KEY_USER_ID, "")
        baseCallApi(foodRepository.getListVoucherByUser(userID),
            onSuccess = { response ->
                _listVoucher.value = response?.data
                _coupon.value = _listVoucher.value?.firstOrNull()?.description
                    ?: view.getStringRes(R.string.no_discount_code)

            },
            onError = {

            })
    }

    override fun listVoucher() = _listVoucher

    override fun coupon() = _coupon

}