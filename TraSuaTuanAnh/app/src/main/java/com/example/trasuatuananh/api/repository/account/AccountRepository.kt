package com.example.trasuatuananh.api.repository.account

import com.example.trasuatuananh.api.model.response.AccountResponse
import com.example.trasuatuananh.api.model.response.BaseResponse
import com.example.trasuatuananh.api.model.response.LoginResponse
import com.example.trasuatuananh.api.model.response.RegisterResponse
import retrofit2.Call

interface AccountRepository {
    fun register(
        phoneNumber: String?,
        userName: String?,
        password: String?
    ): Call<BaseResponse<RegisterResponse>>

    fun login(
        phoneNumber: String?,
        password: String?,
    ): Call<BaseResponse<LoginResponse>>

    fun loginToken(): Call<BaseResponse<LoginResponse>>

    fun getAccount(): Call<BaseResponse<AccountResponse>>
}