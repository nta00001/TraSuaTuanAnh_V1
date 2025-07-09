package com.example.trasuatuananh.ui.home.location.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.trasuatuananh.base.AppBehaviorOnServiceError
import com.example.trasuatuananh.base.BasePresenter
import com.example.trasuatuananh.base.BaseView

interface AddressContract {
    interface View : BaseView, AppBehaviorOnServiceError {
        fun onBackClick()
        fun getStringRes(id: Int): String

    }

    interface Presenter : BasePresenter {
        fun streetName(): MutableLiveData<String>
        fun chooseLocation(): MutableLiveData<String>
        fun address(): LiveData<String>
        fun setStreetName(value: String)
        fun updateAddress()
        fun setChooseLocation(value: String)

    }
}