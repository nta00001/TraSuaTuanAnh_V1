package com.example.trasuatuananh.api.model.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class OrderDetailRequest(
    @Expose
    @SerializedName("orderId")
    val orderId: String,
)