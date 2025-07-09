package com.example.trasuatuananh.api.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MilkTea(
    @Expose
    @SerializedName("id")
    val id: String,

    @Expose
    @SerializedName("tenMon")
    val nameFood: String,

    @Expose
    @SerializedName("moTa")
    val description: String,

    @Expose
    @SerializedName("gia")
    val price: Double,

    @Expose
    @SerializedName("loai")
    val type: String
)

