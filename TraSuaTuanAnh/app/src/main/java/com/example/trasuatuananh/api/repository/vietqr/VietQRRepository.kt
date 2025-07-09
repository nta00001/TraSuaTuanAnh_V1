package com.example.trasuatuananh.api.repository.vietqr

import retrofit2.Call
import com.example.trasuatuananh.api.model.response.QRResponse

interface VietQRRepository {

    fun generateQRCode(
        accountNo: String,
        accountName: String,
        acqId: Int,
        amount: String,
        addInfo: String
    ): Call<QRResponse>
}