package com.example.trasuatuananh.ui.home.listfood.bottomsheet

import androidx.lifecycle.MutableLiveData
import com.example.trasuatuananh.api.model.Food
import com.example.trasuatuananh.base.CommonPresenter
import com.example.trasuatuananh.util.Constants

class IngredientTypePresenter(
    private val view: IngredientTypeContract.View,
    private val food: Food?
) : CommonPresenter(), IngredientTypeContract.Presenter {

    private val _food = MutableLiveData<Food>(food)
    private val _size = MutableLiveData<String>().apply {
        value = food?.size ?: Constants.Type.SIZE_M
    }

    private val _quantity = MutableLiveData<String>().apply {
        value = if (food?.quantity == 0) {
            "1"
        } else {
            food?.quantity.toString()
        }
    }


    override fun updateFood() {
        _food.value = _food.value?.copy(
            quantity = _quantity.value?.toIntOrNull() ?: 1,
            size = _size.value,
        )
    }


    override fun subQuantity() {
        val current = _quantity.value?.toIntOrNull() ?: 1
        if (current > 1) { // Đảm bảo không giảm dưới 1
            _quantity.value = (current - 1).toString()
        }
    }

    override fun addQuantity() {
        val current = _quantity.value?.toIntOrNull() ?: 1
        _quantity.value = (current + 1).toString()
    }


    override fun setSize(size: String) {
        _size.value = size
    }

    override fun food() = _food

    override fun size() = _size

    override fun quantity() = _quantity
}