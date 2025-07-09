package com.example.trasuatuananh.api.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BillResponse(
    @Expose
    @SerializedName("success")
    var success: Boolean? = null,

    @Expose
    @SerializedName("message")
    var message: String? = null,

    @Expose
    @SerializedName("data")
    var data: String? = null,

    @Expose
    @SerializedName("errors")
    var errors: String? = null
)
