package com.example.trasuatuananh.ui.register.user

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.example.trasuatuananh.base.AppBehaviorOnServiceError
import com.example.trasuatuananh.base.BasePresenter
import com.example.trasuatuananh.base.BaseView

class RegisterContract {
    interface View : BaseView, AppBehaviorOnServiceError {
        fun registerSuccessful()
        fun openFragment(fragment: Fragment)
        fun showDiaLogInValid(message: String)
        fun getStringRes(id: Int): String
        fun getViewContext(): Context?
        fun onBackClicked()

    }

    interface Presenter : BasePresenter {
        fun register()
        fun userName(): MutableLiveData<String>
        fun account(): MutableLiveData<String>
        fun password(): MutableLiveData<String>
    }
}