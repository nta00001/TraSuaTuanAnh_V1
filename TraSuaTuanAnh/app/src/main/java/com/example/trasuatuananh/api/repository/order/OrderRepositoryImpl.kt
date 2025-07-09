package com.example.trasuatuananh.api.repository.order

import com.example.trasuatuananh.api.ApiService
import com.example.trasuatuananh.api.RetrofitInstance
import com.example.trasuatuananh.api.model.request.HistoryOrderRequest
import com.example.trasuatuananh.api.model.request.OrderDetailRequest
import com.example.trasuatuananh.api.model.response.BaseResponse
import com.example.trasuatuananh.api.model.response.HistoryOrderResponse
import com.example.trasuatuananh.api.model.response.OrderDetailResponse
import com.example.trasuatuananh.util.Constants
import com.example.trasuatuananh.util.SharedPreferencesUtils
import retrofit2.Call

class OrderRepositoryImpl(
    private val apiService: ApiService = RetrofitInstance.api
) : OrderRepository {
    override fun getHistoryOrder(): Call<BaseResponse<List<HistoryOrderResponse>>> {
        val historyOrderRequest =
            HistoryOrderRequest(userId = SharedPreferencesUtils.get(Constants.KEY.KEY_USER_ID, ""))
        return apiService.getHistoryOrder(historyOrderRequest)
    }

    override fun getOrderDetail(orderId: String): Call<BaseResponse<OrderDetailResponse>> {
        val orderDetailRequest = OrderDetailRequest(orderId)
        return apiService.getOrderDetail(orderDetailRequest)
    }

}