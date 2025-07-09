package com.example.trasuatuananh.base

open class CommonNewUIAppBehaviorOnServiceError(fragment: BaseFragment) :
    AppBehaviorOnServiceError {

    private val appBehaviorOnServiceError by lazy {
        NewUIAppBehaviorOnServiceError(fragment.requireActivity() as BaseActivity)
    }

    override fun handleServiceError(exception: BaseException) {
        appBehaviorOnServiceError.handleServiceError(exception)
    }
}