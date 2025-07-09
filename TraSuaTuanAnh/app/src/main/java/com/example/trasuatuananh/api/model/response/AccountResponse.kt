package com.example.trasuatuananh.api.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AccountResponse(
    @Expose
    @SerializedName("id")
    val userId: String,

    @Expose
    @SerializedName("phoneNumber")
    val phoneNumber: String,

    @Expose
    @SerializedName("username")
    val username: String,

    ) {
}