package com.example.trasuatuananh.ui.home.bestselling

import androidx.lifecycle.LiveData
import com.example.trasuatuananh.api.model.Food
import com.example.trasuatuananh.base.AppBehaviorOnServiceError
import com.example.trasuatuananh.base.BasePresenter
import com.example.trasuatuananh.base.BaseView

interface BestSellingContract {
    interface View : BaseView, AppBehaviorOnServiceError {

    }

    interface Presenter : BasePresenter {
        fun getUserName():LiveData<String>
        fun getListBestSelling()
        fun listBestSelling(): LiveData<List<Food>>
    }
}