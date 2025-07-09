package com.example.trasuatuananh.api.model.address

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TinhTp(
    @Expose
    @SerializedName("name")
    val name: String,

    @Expose
    @SerializedName("slug")
    val slug: String,

    @Expose
    @SerializedName("type")
    val type: String,

    @Expose
    @SerializedName("name_with_type")
    val name_with_type: String,

    @Expose
    @SerializedName("code")
    val code: String,

    @Expose
    @SerializedName("quan-huyen")
    val quan_huyen: Map<String, QuanHuyen>
)
