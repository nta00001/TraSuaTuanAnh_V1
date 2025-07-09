package com.example.trasuatuananh.base

import com.example.trasuatuananh.R
import com.example.trasuatuananh.util.ResultCode


open class NewUIAppBehaviorOnServiceError(private val activity: BaseActivity) :
    AppBehaviorOnServiceError {

    override fun handleServiceError(exception: BaseException) {
        when (exception.code.toString()) {
            ResultCode.ERROR_401 -> {
                activity.showAlertDialogNew(
                    icon = R.drawable.ic_unsuccessfull,
                    title = activity.getString(R.string.app_notify_title),
                    message = activity.getString(R.string.sever_error),
                    textTopButton = activity.getString(R.string.common_close),
                    autoCancel = false,
                )
            }

            ResultCode.ERROR_500 -> {
                activity.showAlertDialogNew(
                    icon = R.drawable.ic_unsuccessfull,
                    title = activity.getString(R.string.app_notify_title),
                    message = activity.getString(R.string.sever_error),
                    textTopButton = activity.getString(R.string.common_close),
                    autoCancel = false,
                )
            }

            ResultCode.ERROR_403 -> {
                activity.showAlertDialogNew(
                    icon = R.drawable.ic_unsuccessfull,
                    title = activity.getString(R.string.app_notify_title),
                    message = activity.getString(R.string.login_failed),
                    textTopButton = activity.getString(R.string.common_close),
                    autoCancel = false,
                )
            }

            ResultCode.ERROR_400 -> {
                activity.showAlertDialogNew(
                    icon = R.drawable.ic_unsuccessfull,
                    title = activity.getString(R.string.app_notify_title),
                    message = activity.getString(R.string.register_failed),
                    textTopButton = activity.getString(R.string.common_close),
                    autoCancel = false,
                )
            }

            else -> {
                exception.message.let { activity.showMessage(it) }
            }
        }
    }
}