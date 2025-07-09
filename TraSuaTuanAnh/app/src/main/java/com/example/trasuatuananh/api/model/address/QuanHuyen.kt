package com.example.trasuatuananh.api.model.address

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class QuanHuyen(
    @Expose
    @SerializedName("name")
    val name: String,

    @Expose
    @SerializedName("type")
    val type: String,

    @Expose
    @SerializedName("slug")
    val slug: String,

    @Expose
    @SerializedName("name_with_type")
    val name_with_type: String,

    @Expose
    @SerializedName("path")
    val path: String,

    @Expose
    @SerializedName("path_with_type")
    val path_with_type: String,

    @Expose
    @SerializedName("code")
    val code: String,

    @Expose
    @SerializedName("parent_code")
    val parent_code: String,

    @Expose
    @SerializedName("xa-phuong")
    val xa_phuong: Map<String, XaPhuong>
)
