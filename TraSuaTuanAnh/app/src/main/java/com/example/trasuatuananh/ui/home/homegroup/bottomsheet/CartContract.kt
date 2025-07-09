package com.example.trasuatuananh.ui.home.homegroup.bottomsheet

import androidx.lifecycle.LiveData
import com.example.trasuatuananh.api.model.Food
import com.example.trasuatuananh.base.BasePresenter
import com.example.trasuatuananh.base.BaseView

interface CartContract {
    interface View : BaseView {

    }

    interface Presenter : BasePresenter {
        fun listFood(): LiveData<List<Food>>
        fun addAmountFood(food: Food)
        fun subAmountFood(food: Food)
        fun updateFood(food: Food)
        fun removeFood(position: Int)

    }
}