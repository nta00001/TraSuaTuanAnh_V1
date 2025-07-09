package com.example.trasuatuananh.api.repository.food

import com.example.trasuatuananh.api.model.Food
import com.example.trasuatuananh.api.model.response.BaseResponse
import com.example.trasuatuananh.api.model.response.BillAdminResponse
import com.example.trasuatuananh.api.model.response.LoginResponse
import com.example.trasuatuananh.api.model.response.RevenueResponse
import com.example.trasuatuananh.api.model.response.VoucherResponse
import retrofit2.Call

interface FoodRepository {
    fun getListFood(): Call<BaseResponse<List<Food>>>
    fun getListHotFood(): Call<BaseResponse<List<Food>>>
    fun getListVoucherByUser(userId: String): Call<BaseResponse<List<VoucherResponse>>>
    fun submitOrder(
        address: String?,
        date: String?,
        paymentMethod: String?,
        discountAmount: Double?,
        totalAmount: Double?,
        voucherId: String?,
        listFood: List<Food>?
    ): Call<BaseResponse<String>>







    fun getRevenue(
        fromDate: String,
        toDate: String
    ): Call<List<RevenueResponse>>

    fun getListOrderAdmin(
        sdtNgNhan: String?,
        status: Int?,
    ): Call<List<BillAdminResponse>>

    fun confirmOrder(id: String): Call<LoginResponse>
    fun cancelOrder(id: String): Call<LoginResponse>
}