package com.example.trasuatuananh.api.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @Expose
    @SerializedName("statusCode")
    val statusCode: Int,

    @Expose
    @SerializedName("success")
    val success: Boolean,

    @Expose
    @SerializedName("message")
    val message: String,

    @Expose
    @SerializedName("data")
    val data: T? = null
)