package com.example.trasuatuananh.api.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MonAn(
    @SerializedName("id")
    @Expose
    val id: String,

    @SerializedName("tenMon")
    @Expose
    val tenMon: String,

    @SerializedName("moTa")
    @Expose
    val moTa: String,

    @SerializedName("gia")
    @Expose
    val gia: Int,

    @SerializedName("loai")
    @Expose
    val loai: String
) : Parcelable
