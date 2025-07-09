package com.example.trasuatuananh.api.repository.food

import com.example.trasuatuananh.api.ApiService
import com.example.trasuatuananh.api.RetrofitInstance
import com.example.trasuatuananh.api.model.Food
import com.example.trasuatuananh.api.model.request.OrderRequest
import com.example.trasuatuananh.api.model.request.RevenueRequest
import com.example.trasuatuananh.api.model.request.VoucherRequest
import com.example.trasuatuananh.api.model.response.BaseResponse
import com.example.trasuatuananh.api.model.response.BillAdminResponse
import com.example.trasuatuananh.api.model.response.LoginResponse
import com.example.trasuatuananh.api.model.response.RevenueResponse
import com.example.trasuatuananh.api.model.response.VoucherResponse
import com.example.trasuatuananh.util.Constants
import com.example.trasuatuananh.util.SharedPreferencesUtils
import retrofit2.Call

class FoodRepositoryImpl(
    private val apiService: ApiService = RetrofitInstance.api
) : FoodRepository {
    override fun getListFood(): Call<BaseResponse<List<Food>>> {
        return apiService.getListFood()
    }

    override fun getListHotFood(): Call<BaseResponse<List<Food>>> {
        return apiService.getListHotFood()
    }

    override fun getListVoucherByUser(userId: String): Call<BaseResponse<List<VoucherResponse>>> {
        val voucherRequest = VoucherRequest(userId)
        return apiService.getListVoucher(voucherRequest)
    }



    override fun submitOrder(
        address: String?,
        date: String?,
        paymentMethod: String?,
        discountAmount: Double?,
        totalAmount: Double?,
        voucherId: String?,
        listFood: List<Food>?
    ): Call<BaseResponse<String>> {
        val orderRequest = OrderRequest(
            userId = SharedPreferencesUtils.get(Constants.KEY.KEY_USER_ID, ""),
            userName = SharedPreferencesUtils.get(Constants.KEY.KEY_USER_NAME, ""),
            phoneNumber = SharedPreferencesUtils.get(Constants.KEY.KEY_PHONE_NUMBER, ""),
            address = address ?: "",
            orderDate = date ?: "",
            paymentMethod = paymentMethod ?: "",
            orderStatus = "0",
            discountAmount = discountAmount ?: 0.0,
            totalAmount = totalAmount ?: 0.0,
            voucherId = voucherId ?: null,
            foods = listFood ?: emptyList()

        )
        return apiService.submitOrder(orderRequest)
    }


    override fun getRevenue(fromDate: String, toDate: String): Call<List<RevenueResponse>> {
        return apiService.getRevenue(RevenueRequest(fromDate, toDate))
    }

    override fun getListOrderAdmin(
        sdtNgNhan: String?,
        status: Int?
    ): Call<List<BillAdminResponse>> {
        return apiService.getListOrderAdmin(status, sdtNgNhan)
    }

    override fun confirmOrder(id: String): Call<LoginResponse> {
        return apiService.confirmOrder(id)
    }

    override fun cancelOrder(id: String): Call<LoginResponse> {
        return apiService.cancelOrder(id)
    }

}