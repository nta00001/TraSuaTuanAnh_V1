package com.example.trasuatuananh.api.model.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class QRRequest(
    @SerializedName("accountNo")
    @Expose val accountNo: String,

    @SerializedName("accountName")
    @Expose val accountName: String,

    @SerializedName("acqId")
    @Expose val acqId: Int,

    @SerializedName("amount")
    @Expose val amount: Int,

    @SerializedName("addInfo")
    @Expose val addInfo: String,

    @SerializedName("format")
    @Expose val format: String = "text",

    @SerializedName("template")
    @Expose val template: String = "compact"
)
