package com.example.trasuatuananh.api.model.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BillAdminRequest(
    @SerializedName("sdtNguoiNhan")
    @Expose
    val sdtNguoiNhan: String? = null,

    @SerializedName("status")
    @Expose
    val status: Int? = null,
)
