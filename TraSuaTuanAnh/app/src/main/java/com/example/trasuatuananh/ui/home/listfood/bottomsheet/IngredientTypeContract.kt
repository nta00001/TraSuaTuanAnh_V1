package com.example.trasuatuananh.ui.home.listfood.bottomsheet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.trasuatuananh.api.model.Food
import com.example.trasuatuananh.base.BasePresenter
import com.example.trasuatuananh.base.BaseView

interface IngredientTypeContract {

    interface View : BaseView {

    }

    interface Presenter : BasePresenter {
        fun updateFood()
        fun subQuantity()
        fun addQuantity()
        fun setSize(size: String)
        fun food(): LiveData<Food>
        fun size(): LiveData<String>
        fun quantity(): MutableLiveData<String>
    }
}