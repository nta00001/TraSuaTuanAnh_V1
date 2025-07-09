package com.example.trasuatuananh.ui.main

import com.example.trasuatuananh.base.AppBehaviorOnServiceError
import com.example.trasuatuananh.base.BasePresenter
import com.example.trasuatuananh.base.BaseView

interface MainContract {
    interface View: BaseView,AppBehaviorOnServiceError{

    }
    interface Presenter:BasePresenter{

    }
}