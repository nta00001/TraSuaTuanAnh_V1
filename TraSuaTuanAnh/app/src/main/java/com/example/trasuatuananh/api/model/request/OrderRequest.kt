package com.example.trasuatuananh.api.model.request

import android.os.Parcelable
import com.example.trasuatuananh.api.model.Food
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderRequest(

    @Expose
    @SerializedName("userId")
    val userId: String,

    @Expose
    @SerializedName("userName")
    val userName: String,

    @Expose
    @SerializedName("phoneNumber")
    val phoneNumber: String,

    @Expose
    @SerializedName("address")
    val address: String,

    @Expose
    @SerializedName("orderDate")
    val orderDate: String,

    @Expose
    @SerializedName("paymentMethod")
    val paymentMethod: String,

    @Expose
    @SerializedName("orderStatus")
    val orderStatus: String,

    @Expose
    @SerializedName("discountAmount")
    val discountAmount: Double,

    @Expose
    @SerializedName("totalAmount")
    val totalAmount: Double,

    @Expose
    @SerializedName("voucherId")
    val voucherId: String?,

    @Expose
    @SerializedName("foods")
    val foods: List<Food>
) : Parcelable