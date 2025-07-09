package com.example.trasuatuananh.api.model.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @Expose
    @SerializedName("phoneNumber")
    val phoneNumber: String? = null,

    @Expose
    @SerializedName("password")
    val passWord: String? = null
)
