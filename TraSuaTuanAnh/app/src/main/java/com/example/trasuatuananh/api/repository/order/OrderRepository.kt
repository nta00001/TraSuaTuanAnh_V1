package com.example.trasuatuananh.api.repository.order

import com.example.trasuatuananh.api.model.response.BaseResponse
import com.example.trasuatuananh.api.model.response.HistoryOrderResponse
import com.example.trasuatuananh.api.model.response.OrderDetailResponse
import retrofit2.Call

interface OrderRepository {
    fun getHistoryOrder(): Call<BaseResponse<List<HistoryOrderResponse>>>
    fun getOrderDetail(orderId: String): Call<BaseResponse<OrderDetailResponse>>
}