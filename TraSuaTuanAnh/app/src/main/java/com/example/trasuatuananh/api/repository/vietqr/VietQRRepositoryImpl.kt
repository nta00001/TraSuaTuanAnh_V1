package com.example.trasuatuananh.api.repository.vietqr

import com.example.trasuatuananh.api.VietQRClient
import com.example.trasuatuananh.api.VietQRService
import com.example.trasuatuananh.api.model.request.QRRequest
import com.example.trasuatuananh.api.model.response.QRResponse
import com.example.trasuatuananh.util.StringUtils
import retrofit2.Call

class VietQRRepositoryImpl(
    private val vietQRService: VietQRService = VietQRClient.vietQRService
) : VietQRRepository {
    override fun generateQRCode(
        accountNo: String,
        accountName: String,
        acqId: Int,
        amount: String,
        addInfo: String
    ): Call<QRResponse> {
        val request = QRRequest(
            accountNo = accountNo,
            accountName = accountName,
            acqId = acqId,
            amount = StringUtils.removeCommasAndDots(amount),
            addInfo = addInfo
        )
        return vietQRService.generateQRCode(request)
    }
}