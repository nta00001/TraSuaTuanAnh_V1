package com.example.trasuatuananh.api.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    @SerializedName("soLuong")
    @Expose
    val soLuong: Int,

    @SerializedName("size")
    @Expose
    val size: String,

    @SerializedName("idMonAn")
    @Expose
    val idMonAn: String
) : Parcelable
