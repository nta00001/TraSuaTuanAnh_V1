package com.example.trasuatuananh.ui.payment

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.trasuatuananh.R
import com.example.trasuatuananh.api.model.Food
import com.example.trasuatuananh.api.model.Item
import com.example.trasuatuananh.api.model.response.VoucherResponse
import com.example.trasuatuananh.api.repository.food.FoodRepository
import com.example.trasuatuananh.base.CommonPresenter
import com.example.trasuatuananh.util.Constants
import com.example.trasuatuananh.util.SharedPreferencesUtils
import com.example.trasuatuananh.util.StringUtils
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PaymentPresenter(
    private val view: PaymentContract.View,
    listFood: List<Food>,
    private val foodRepository: FoodRepository
) : CommonPresenter(view, view), PaymentContract.Presenter {
    private val _isQrSelected = MutableLiveData<Boolean>()
    private val _listFood = MutableLiveData<List<Food>>(listFood)
    private val _listItem = MutableLiveData<List<Item>>()
    private val _address = MutableLiveData<String>().apply {
        value = SharedPreferencesUtils.get(Constants.KEY.KEY_ADDRESS, "")
    }
    private val _info = MutableLiveData<String>(
        SharedPreferencesUtils.get(Constants.KEY.KEY_USER_NAME, "") + " | " +
                SharedPreferencesUtils.get(Constants.KEY.KEY_PHONE_NUMBER, "")
    )
    private val _phoneNumber = MutableLiveData<String>()
    private val _voucher = MutableLiveData<VoucherResponse>()
    private val _placeOrder = MediatorLiveData<String>().apply {
        value = view.getStringRes(R.string.place_order)
    }
    private val _totalAmount = MediatorLiveData<String>()
    private val _shippingFee = MutableLiveData<String>("15.000")
    private val _discountFee = MutableLiveData<String>("0")
    private val _totalItemAmount = MediatorLiveData<String>()
    private val _listVoucher = MutableLiveData<List<VoucherResponse>>()


    init {
        _totalAmount.addSource(_shippingFee) { updateTotalAmount() }
        _totalAmount.addSource(_discountFee) { updateTotalAmount() }
        _totalAmount.addSource(_totalItemAmount) { updateTotalAmount() }
        _totalItemAmount.addSource(_listFood) { setTotalItemAmount() }
        updateTotalAmount()
    }


    override fun listFood() = _listFood
    override fun listItem() = _listItem
    override fun isQrSelected() = _isQrSelected
    override fun address() = _address
    override fun phoneNumber() = _phoneNumber
    override fun voucher() = _voucher
    override fun setVoucher(value: VoucherResponse) {
        _voucher.value = value
    }

    override fun placeOrder() = _placeOrder
    override fun setPlaceOrder(value: String) {
        _placeOrder.value = value
    }

    override fun totalAmount() = _totalAmount
    override fun shippingFee() = _shippingFee
    override fun discountFee() = _discountFee
    override fun setDiscountFee(value: String) {
        _discountFee.value = value
    }

    override fun totalItemAmount() = _totalItemAmount
    override fun info() = _info
    override fun listVoucher() = _listVoucher


    override fun setTotalItemAmount() {
        _totalItemAmount.value = _listFood.value?.let {
            StringUtils.calculateTotalPrice(it)
        } ?: "0"
    }


    override fun setIsQrSelected(value: Boolean) {
        _isQrSelected.value = value
    }

    private fun updateTotalAmount() {
        val shippingFee = StringUtils.parseMoney(_shippingFee.value)
        val discountFee = StringUtils.parseMoney(_discountFee.value)
        val totalItemAmount = StringUtils.parseMoney(_totalItemAmount.value)

        val totalAmount = shippingFee - discountFee + totalItemAmount
        _totalAmount.value = StringUtils.formatMoney(totalAmount)
    }


    override fun removeBubbleTea(position: Int) {
        val currentList = _listFood.value?.toMutableList() ?: return
        if (position in currentList.indices) {
            currentList.removeAt(position)
            _listFood.value = currentList
        }
    }

    override fun submitOrder() {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val formattedDate = formatter.format(Date())
        baseCallApi(
            foodRepository.submitOrder(
                address = _address.value,
                date = formattedDate,
                paymentMethod = view.getStringRes(R.string.payment_cod),
                discountAmount = _discountFee.value?.let { StringUtils.formatMoneyClean(it) },
                totalAmount = _totalAmount.value?.let { StringUtils.formatMoneyClean(it) },
                voucherId = _voucher.value?.voucherId,
                listFood = _listFood.value
            ), onSuccess = { response ->
                _listFood.value = emptyList()
                _shippingFee.value = "0"
                _discountFee.value = "0"
                response?.message?.let { view.showDiaLog(it) }
            },
            onError = {

            }
        )
    }

    override fun getListVoucher() {
        val userID = SharedPreferencesUtils.get(Constants.KEY.KEY_USER_ID, "")
        baseCallApi(foodRepository.getListVoucherByUser(userID),
            onSuccess = { response ->
                _listVoucher.value = response?.data
            },
            onError = {

            })
    }


    override fun setListItem(listFood: List<Food>) {
        _listItem.value = listFood.map { food ->
            Item(
                soLuong = food.quantity,
                size = food.size ?: "M",
                idMonAn = food.id.toString()
            )
        }
    }

}