package com.example.trasuatuananh.api.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item2(
    @SerializedName("soLuong")
    @Expose
    val soLuong: Int,

    @SerializedName("size")
    @Expose
    val size: String,

    @SerializedName("monAn")
    @Expose
    val monAn: MonAn
) : Parcelable
