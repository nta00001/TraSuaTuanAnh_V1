package com.example.trasuatuananh.api.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @Expose
    @SerializedName("token")
    var token: String? = null,
)
