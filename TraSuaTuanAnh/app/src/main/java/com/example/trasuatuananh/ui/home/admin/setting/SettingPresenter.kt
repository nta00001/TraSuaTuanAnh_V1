package com.example.trasuatuananh.ui.home.admin.setting

import com.example.trasuatuananh.base.CommonPresenter

class SettingPresenter (
    private val view: SettingContract.View
):CommonPresenter(view,view),SettingContract.Presenter{
}