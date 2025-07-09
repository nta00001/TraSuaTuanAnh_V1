package com.example.trasuatuananh.api.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class IngredientType(
    var quantity: String? = null,
    var type: String? = null,
) : Parcelable
