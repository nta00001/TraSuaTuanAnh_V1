package com.example.trasuatuananh.api.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Food(
    @SerializedName("id")
    @Expose
    val id: String,

    @SerializedName("foodName")
    @Expose
    val foodName: String,

    @SerializedName("description")
    @Expose
    val description: String,

    @SerializedName("price")
    @Expose
    val price: Double,

    @SerializedName("size")
    @Expose
    var size: String?,

    @SerializedName("quantity")
    @Expose
    var quantity: Int,

    @SerializedName("imageUrl")
    @Expose
    val imageUrl: String
) : Parcelable
