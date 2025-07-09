package com.example.trasuatuananh.ui.info

import com.example.trasuatuananh.base.CommonPresenter
import com.example.trasuatuananh.util.Constants
import com.example.trasuatuananh.util.SharedPreferencesUtils
import kotlinx.coroutines.flow.MutableStateFlow


class InfoPresenter(
    private val view: InfoContract.View
) : CommonPresenter(view, view), InfoContract.Presenter {
    private val _userName =
        MutableStateFlow(SharedPreferencesUtils.get(Constants.KEY.KEY_USER_NAME, ""))
    private val _phoneNumber =
        MutableStateFlow(SharedPreferencesUtils.get(Constants.KEY.KEY_PHONE_NUMBER, ""))

    override fun userName() = _userName

    override fun phoneNumber() = _phoneNumber

}