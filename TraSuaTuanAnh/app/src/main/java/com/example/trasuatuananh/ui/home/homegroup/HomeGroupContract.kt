package com.example.trasuatuananh.ui.home.homegroup

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.example.trasuatuananh.api.model.Food
import com.example.trasuatuananh.base.AppBehaviorOnServiceError
import com.example.trasuatuananh.base.BasePresenter
import com.example.trasuatuananh.base.BaseView

interface HomeGroupContract {
    interface View : BaseView, AppBehaviorOnServiceError {

    }

    interface Presenter : BasePresenter {
        fun totalAmount(): LiveData<String>
        fun quantity(): LiveData<String>
        fun getUserInfo()
        fun listFood(): LiveData<List<Food>>
        fun setListFood(listFood: List<Food>)
        fun listFragment(): LiveData<List<() -> Fragment>>
        fun isCartEmpty (): LiveData<Boolean>
        fun setIsCartEmpty (boolean: Boolean)
        fun getListFragmentHome()
    }
}