package com.example.trasuatuananh.api.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class HistoryOrderResponse(
    @SerializedName("orderId")
    @Expose
    val orderId: String,

    @SerializedName("userName")
    @Expose
    val userName: String,

    @SerializedName("phoneNumber")
    @Expose
    val phoneNumber: String,

    @SerializedName("address")
    @Expose
    val address: String,

    @SerializedName("orderDate")
    @Expose
    val orderDate: String, // hoặc Date nếu dùng định dạng date parser

    @SerializedName("paymentMethod")
    @Expose
    val paymentMethod: String,

    @SerializedName("orderStatus")
    @Expose
    val orderStatus: String,

    @SerializedName("discountAmount")
    @Expose
    val discountAmount: Double,

    @SerializedName("totalAmount")
    @Expose
    val totalAmount: Double
)
