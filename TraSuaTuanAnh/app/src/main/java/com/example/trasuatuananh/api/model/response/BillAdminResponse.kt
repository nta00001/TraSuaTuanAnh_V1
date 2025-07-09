package com.example.trasuatuananh.api.model.response

import android.os.Parcelable
import com.example.trasuatuananh.api.model.Item2
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BillAdminResponse(
    @SerializedName("id")
    @Expose
    val id: String,

    @SerializedName("sdtNgNhan")
    @Expose
    val sdtNgNhan: String,

    @SerializedName("orderBy")
    @Expose
    val orderBy: String,

    @SerializedName("date")
    @Expose
    val date: String,

    @SerializedName("status")
    @Expose
    val status: Int,

    @SerializedName("address")
    @Expose
    val address: String,

    @SerializedName("idVoucher")
    @Expose
    val idVoucher: String?,

    @SerializedName("item")
    @Expose
    val items: List<Item2>
) : Parcelable
