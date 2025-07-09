package com.example.trasuatuananh.api.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class QRData(
    @SerializedName("qrCode")
    @Expose val qrCode: String,

    @SerializedName("qrDataURL")
    @Expose val qrDataURL: String


)
