package com.example.trasuatuananh.ui.payment.qr

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.lifecycle.MutableLiveData
import com.example.trasuatuananh.R
import com.example.trasuatuananh.api.Config
import com.example.trasuatuananh.api.model.Food
import com.example.trasuatuananh.api.repository.food.FoodRepository
import com.example.trasuatuananh.api.repository.vietqr.VietQRRepository
import com.example.trasuatuananh.base.CommonPresenter
import com.example.trasuatuananh.util.StringUtils
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PaymentQrPresenter(
    private val view: PaymentQrContract.View,
    private val address: String?,
    private val discountAmount: String?,
    private val totalAmount: String?,
    private val voucherId: String?,
    private val listFood: List<Food>?,
    private val vietQRRepository: VietQRRepository,
    private val foodRepository: FoodRepository
) : CommonPresenter(view, view), PaymentQrContract.Presenter {
    private val _price = MutableLiveData<String>(totalAmount)
    private val _message = MutableLiveData<String>(view.getStringRes(R.string.payment_bubble_tea))

    override fun price() = _price

    override fun message() = _message

    override fun getQrCode() {
        // Gọi API thông qua repository
        baseCallApi(vietQRRepository.generateQRCode(
            Config.ACCOUNT_NUMBER,
            Config.ACCOUNT_NAME,
            Config.BANK_ID,
            _price.value ?: "0",
            Config.DESCRIPTION
        ), onSuccess = { response ->
            val qrCodeData = response?.data?.qrDataURL
            qrCodeData?.let {
                // Loại bỏ phần tiền tố "data:image/png;base64," (nếu có)
                val base64Image = it.removePrefix("data:image/png;base64,")

                // Giải mã chuỗi Base64 thành mảng byte
                val decodedBytes = Base64.decode(base64Image, Base64.DEFAULT)

                // Chuyển mảng byte thành Bitmap
                val bitmap =
                    BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)

                bitmap?.let {
                    view.setImageQr(it)
                }

            }
        }, onError = {
        })


    }

    override fun submitOrder() {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val formattedDate = formatter.format(Date())
        baseCallApi(
            foodRepository.submitOrder(
                address = address,
                date = formattedDate,
                paymentMethod = view.getStringRes(R.string.payment_qr),
                discountAmount = StringUtils.formatMoneyClean(discountAmount ?: "0"),
                totalAmount = StringUtils.formatMoneyClean(totalAmount ?: "0"),
                voucherId = voucherId,
                listFood = listFood
            ),
            onSuccess = {
                view.sendListFoodBroadcast(emptyList())
            },
            onError = {

            }
        )
    }
}


