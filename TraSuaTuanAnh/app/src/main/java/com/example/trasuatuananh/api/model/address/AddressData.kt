package com.example.trasuatuananh.api.model.address

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AddressData(
    @Expose
    @SerializedName("data")
    val data: Map<String, TinhTp>
) {
}