package com.example.trasuatuananh.ui.home.homegroup.bottomsheet

import androidx.lifecycle.MutableLiveData
import com.example.trasuatuananh.api.model.Food
import com.example.trasuatuananh.base.CommonPresenter

class CartPresenter(
    private val view: CartContract.View,
    listFood: List<Food>
) : CommonPresenter(), CartContract.Presenter {
    private val _listFood = MutableLiveData<List<Food>>(listFood)
    override fun listFood() = _listFood
    override fun addAmountFood(food: Food) {
        val currentQuantity = food.quantity
        food.quantity = currentQuantity + 1
        updateFood(food)
    }

    override fun subAmountFood(food: Food) {
        val currentQuantity = food.quantity
        if (currentQuantity > 1) {
            food.quantity = currentQuantity - 1
        } else {
            food.quantity = 0
        }
        updateFood(food)
    }

    override fun updateFood(food: Food) {
        _listFood.value = _listFood.value
            ?.mapNotNull { item ->
                if (item.foodName == food.foodName) {
                    if (food.quantity == 0) {
                        null
                    } else {
                        item.copy(
                            quantity = food.quantity,
                            size = food.size
                        )
                    }
                } else {
                    item
                }
            }?.toMutableList()
    }

    override fun removeFood(position: Int) {
        val currentList = _listFood.value?.toMutableList() ?: return
        if (position in currentList.indices) {
            currentList.removeAt(position)
            _listFood.value = currentList
        }
    }
}