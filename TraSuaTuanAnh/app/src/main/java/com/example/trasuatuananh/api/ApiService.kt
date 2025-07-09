package com.example.trasuatuananh.api

import com.example.trasuatuananh.api.model.Food
import com.example.trasuatuananh.api.model.response.VoucherResponse
import com.example.trasuatuananh.api.model.request.BillRequest
import com.example.trasuatuananh.api.model.request.HistoryOrderRequest
import com.example.trasuatuananh.api.model.request.LoginRequest
import com.example.trasuatuananh.api.model.request.OrderDetailRequest
import com.example.trasuatuananh.api.model.request.OrderRequest
import com.example.trasuatuananh.api.model.request.RegisterRequest
import com.example.trasuatuananh.api.model.request.RevenueRequest
import com.example.trasuatuananh.api.model.request.VoucherRequest
import com.example.trasuatuananh.api.model.response.AccountResponse
import com.example.trasuatuananh.api.model.response.BaseResponse
import com.example.trasuatuananh.api.model.response.BillAdminResponse
import com.example.trasuatuananh.api.model.response.BillResponse
import com.example.trasuatuananh.api.model.response.HistoryOrderResponse
import com.example.trasuatuananh.api.model.response.LoginResponse
import com.example.trasuatuananh.api.model.response.OrderDetailResponse
import com.example.trasuatuananh.api.model.response.RegisterResponse
import com.example.trasuatuananh.api.model.response.RevenueResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("/api/auth/Login")
    fun loginAccount(@Body loginRequest: LoginRequest): Call<BaseResponse<LoginResponse>>

    @POST("/api/auth/login-with-token")
    fun loginTokenAccount(): Call<BaseResponse<LoginResponse>>

    @POST("/api/auth/register")
    fun registerAccount(@Body registrationRequest: RegisterRequest): Call<BaseResponse<RegisterResponse>>

    @GET("api/auth/userinfo")
    fun getAccount(): Call<BaseResponse<AccountResponse>>

    @GET("/api/food/getAllFood")
    fun getListFood(): Call<BaseResponse<List<Food>>>


    @GET("/api/food/getHotFoods")
    fun getListHotFood(): Call<BaseResponse<List<Food>>>


    @POST("/api/voucher/getVoucherByUser")
    fun getListVoucher(@Body voucherRequest: VoucherRequest): Call<BaseResponse<List<VoucherResponse>>>

    @POST("/api/order/place")
    fun submitOrder(@Body orderRequest: OrderRequest): Call<BaseResponse<String>>

    @POST("/api/order/getOrderByUser")
    fun getHistoryOrder(@Body historyOrderRequest: HistoryOrderRequest): Call<BaseResponse<List<HistoryOrderResponse>>>

    @POST("/api/order/getOrderByOrderId")
    fun getOrderDetail(@Body orderDetailRequest: OrderDetailRequest): Call<BaseResponse<OrderDetailResponse>>
    // cu
//    @POST("/api/Account/Registration")
//    fun registerAccount(@Body registrationRequest: RegisterRequest): Call<RegisterResponse>

//    @POST("/api/Account/Login")
//    fun loginAccount(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("/api/Bill")
    fun submitOrder(@Body billRequest: BillRequest): Call<BillResponse>

    @POST("/api/Bill/NextStep")
    fun confirmOrder(@Query("id") id: String): Call<LoginResponse>

    @POST("/api/Bill/cancel")
    fun cancelOrder(@Query("id") id: String): Call<LoginResponse>

    @POST("/api/ThongKe")
    fun getRevenue(@Body revenueRequest: RevenueRequest): Call<List<RevenueResponse>>

//    @GET("/api/Account/Token")
//    fun loginTokenAccount(): Call<LoginResponse>

//    @GET("/api/Account")
//    fun getAccount(): Call<AccountResponse>

//    @GET("/api/MonAn")
//    fun getListFood(): Call<BaseResponse<List<food>>>

//    @GET("/api/voucher")
//    fun getListVoucher(): Call<List<Coupon>>

    @GET("/api/Bill/BillsByAdmin")
    fun getListOrderAdmin(
        @Query("Status") status: Int?,
        @Query("SdtNguoiNhan") sdtNguoiNhan: String?
    ): Call<List<BillAdminResponse>>

}