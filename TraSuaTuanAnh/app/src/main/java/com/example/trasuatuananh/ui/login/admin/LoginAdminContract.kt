package com.example.trasuatuananh.ui.login.admin

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.trasuatuananh.base.AppBehaviorOnServiceError
import com.example.trasuatuananh.base.BasePresenter
import com.example.trasuatuananh.base.BaseView

interface LoginAdminContract {
    interface View: BaseView, AppBehaviorOnServiceError {
        fun showDiaLogInValid(message: String)
        fun getStringRes(id: Int): String
        fun loginAdminSuccessful()
        fun getViewContext(): Context?
    }
    interface Presenter: BasePresenter {
        fun account(): MutableLiveData<String>
        fun passWord(): MutableLiveData<String>

        fun loginAdmin()
    }
}