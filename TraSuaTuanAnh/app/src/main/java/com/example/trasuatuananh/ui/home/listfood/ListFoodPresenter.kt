package com.example.trasuatuananh.ui.home.listfood

import androidx.lifecycle.MutableLiveData
import com.example.trasuatuananh.api.model.Food
import com.example.trasuatuananh.api.repository.food.FoodRepository
import com.example.trasuatuananh.base.CommonPresenter
import com.example.trasuatuananh.util.Constants

class ListFoodPresenter(
    private val view: ListFoodContract.View,
    private val foodRepository: FoodRepository
) :
    CommonPresenter(view, view), ListFoodContract.Presenter {
    private var _listFood = MutableLiveData<MutableList<Food>>()
    override fun listFood() = _listFood

    override fun updateListFood(listFood: List<Food>) {
        _listFood.value = _listFood.value?.map { item ->
            listFood.find { it.foodName == item.foodName }?.let {
                item.copy(
                    quantity = it.quantity,
                    size = it.size
                )
            } ?: item.copy(
                quantity = 0,
                size = "M"
            )
        }?.toMutableList()
    }

    override fun getListFood() {
        baseCallApi(foodRepository.getListFood(),
            onSuccess = { response ->
                _listFood.value = response?.data as MutableList<Food>?
            },
            onError = {
            })

    }

    override fun addAmountfood(food: Food) {
        if (food.quantity == 0) {
            view.showBottomSheet(food)
        } else {
            food.quantity++
            updateFood(food)
        }
    }

    override fun subAmountfood(food: Food) {
        food.quantity = (food.quantity - 1).coerceAtLeast(0)
        updateFood(food)
    }


    override fun getBroadcastAction(listFood: MutableList<Food>): Pair<String, List<Food>> {
        val filteredList = listFood.filter { it.quantity > 0 }
        val action = if (filteredList.isNotEmpty()) {
            Constants.Actions.NOTIFY_SHOW_CART
        } else {
            Constants.Actions.NOTIFY_HIDE_CART
        }
        return action to filteredList
    }



    override fun updateFood(food: Food) {
        _listFood.value = _listFood.value?.map { item ->
            if (item.foodName == food.foodName) {
                item.copy(
                    quantity = food.quantity,
                    size = food.size
                )
            } else {
                item
            }
        }?.toMutableList()
    }
}