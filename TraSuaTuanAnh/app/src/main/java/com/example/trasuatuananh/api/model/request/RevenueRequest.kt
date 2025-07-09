package com.example.trasuatuananh.api.model.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RevenueRequest(
    @SerializedName("fromDate")
    @Expose val fromDate: String,

    @SerializedName("toDate")
    @Expose val toDate: String,
) {
}