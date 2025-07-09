package com.example.trasuatuananh.ui.home.search

import com.example.trasuatuananh.base.CommonPresenter

class SearchPresenter(
    private val view: SearchContract.View
) : CommonPresenter(view, view), SearchContract.Presenter {
}