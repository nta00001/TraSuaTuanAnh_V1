package com.example.trasuatuananh.api.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RevenueResponse(
    @SerializedName("date")
    @Expose val date: String,

    @SerializedName("tien")
    @Expose val tien: String,
) {
}