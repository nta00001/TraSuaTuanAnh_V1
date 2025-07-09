package com.example.trasuatuananh.ui.home.listfood

import androidx.lifecycle.LiveData
import com.example.trasuatuananh.api.model.Food
import com.example.trasuatuananh.base.AppBehaviorOnServiceError
import com.example.trasuatuananh.base.BasePresenter
import com.example.trasuatuananh.base.BaseView

interface ListFoodContract {
    interface View : BaseView, AppBehaviorOnServiceError {
        fun showBottomSheet(food: Food)
    }

    interface Presenter : BasePresenter {
        fun getBroadcastAction(listFood: MutableList<Food>): Pair<String, List<Food>>
        fun updateFood(food: Food)
        fun listFood(): LiveData<MutableList<Food>>
        fun updateListFood(listFood: List<Food>)
        fun getListFood()
        fun addAmountfood(food: Food)
        fun subAmountfood(food: Food)
    }
}
