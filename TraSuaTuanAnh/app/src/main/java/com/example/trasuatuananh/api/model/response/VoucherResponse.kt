package com.example.trasuatuananh.api.model.response

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class VoucherResponse(
    @SerializedName("voucherId")
    @Expose
    val voucherId: String,

    @SerializedName("code")
    @Expose
    val code: String,

    @SerializedName("description")
    @Expose
    val description: String,

    @SerializedName("discountAmount")
    @Expose
    val discountAmount: Int,

    @SerializedName("discountPercent")
    @Expose
    val discountPercent: Int,

    @SerializedName("expirationDate")
    @Expose
    val expirationDate: String,

    @SerializedName("remainingUses")
    @Expose
    val remainingUses: Int
):Parcelable
