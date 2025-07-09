package com.example.trasuatuananh.ui.home.admin

import com.example.trasuatuananh.base.AppBehaviorOnServiceError
import com.example.trasuatuananh.base.BasePresenter
import com.example.trasuatuananh.base.BaseView

interface HomeAdminContract {
    interface View: BaseView, AppBehaviorOnServiceError {

    }
    interface Presenter: BasePresenter {


    }
}