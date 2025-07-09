package com.example.trasuatuananh.ui.info

import com.example.trasuatuananh.base.AppBehaviorOnServiceError
import com.example.trasuatuananh.base.BasePresenter
import com.example.trasuatuananh.base.BaseView
import kotlinx.coroutines.flow.StateFlow

interface InfoContract {
    interface View : BaseView, AppBehaviorOnServiceError {

        fun openFragmentHistoryOrder()
    }

    interface Presenter : BasePresenter {
        fun userName(): StateFlow<String>
        fun phoneNumber(): StateFlow<String>

    }
}