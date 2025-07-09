package com.example.trasuatuananh.api.model.response

import com.example.trasuatuananh.api.model.Food
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class OrderDetailResponse(
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
    val orderDate: String,

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
    val totalAmount: Double,

    @SerializedName("foods")
    @Expose
    val foods: List<Food>
)
