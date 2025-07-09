package com.example.trasuatuananh.api.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(
    @Expose
    @SerializedName("id")
    var id: String? = null,

    @Expose
    @SerializedName("username")
    var username: String? = null,

    @Expose
    @SerializedName("phoneNumber")
    var phoneNumber : String? = null
) {
}