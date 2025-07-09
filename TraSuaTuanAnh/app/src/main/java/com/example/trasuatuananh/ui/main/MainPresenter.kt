package com.example.trasuatuananh.ui.main

import com.example.trasuatuananh.base.CommonPresenter

class MainPresenter(
    private val view: MainContract.View
) : CommonPresenter(view, view), MainContract.Presenter {
}