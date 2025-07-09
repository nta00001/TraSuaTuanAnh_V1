package com.example.trasuatuananh.api.model.request

import com.example.trasuatuananh.api.model.Item
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BillRequest(
    @SerializedName("sdtNgNhan")
    @Expose
    val sdtNgNhan: String? = null,

    @SerializedName("status")
    @Expose
    val status: Int? = null,

    @SerializedName("address")
    @Expose
    val address: String? = null,

    @SerializedName("idVoucher")
    @Expose
    val idVoucher: String? = null,

    @SerializedName("item")
    @Expose
    val item: List<Item>? = null
) {
}