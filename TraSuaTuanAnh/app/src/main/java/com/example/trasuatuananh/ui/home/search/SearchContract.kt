package com.example.trasuatuananh.ui.home.search

import com.example.trasuatuananh.base.AppBehaviorOnServiceError
import com.example.trasuatuananh.base.BasePresenter
import com.example.trasuatuananh.base.BaseView

interface SearchContract {
    interface View: BaseView, AppBehaviorOnServiceError {

    }
    interface Presenter: BasePresenter {

    }
}