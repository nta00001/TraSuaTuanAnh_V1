package com.example.trasuatuananh.ui.login.user

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.example.trasuatuananh.base.AppBehaviorOnServiceError
import com.example.trasuatuananh.base.BasePresenter
import com.example.trasuatuananh.base.BaseView

interface LoginContract {
    interface View : BaseView, AppBehaviorOnServiceError {
        fun showDiaLogInValid(message: String)
        fun getStringRes(id: Int): String
        fun getViewContext(): Context?
        fun loginSuccessful()
        fun loginAdminSuccessful()
        fun openFragment(fragment: Fragment)
    }

    interface Presenter : BasePresenter {
        fun login()
        fun loginToken()
        fun account(): MutableLiveData<String>
        fun password(): MutableLiveData<String>
    }

}