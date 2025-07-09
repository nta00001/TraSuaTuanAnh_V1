package com.example.trasuatuananh.api.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BubbleTea(
    var id: String?= null,
    var image: Int? = null,
    var nameTea: String? = null,
    var price: String? = null,
    val description: String? = null,
    var ingredientType: IngredientType? = IngredientType()
) : Parcelable
