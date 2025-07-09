package com.example.trasuatuananh.api

import com.example.trasuatuananh.api.model.request.QRRequest
import com.example.trasuatuananh.api.model.response.QRResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface VietQRService {

    @Headers(
        "x-client-id: 64e3e7b3-fe66-433e-878d-c459ba9b62a4",
        "x-api-key: 09baa585-3f5f-45af-9cdb-ed7456334487"
    )
    @POST("v2/generate")
    fun generateQRCode(@Body request: QRRequest): Call<QRResponse>
}