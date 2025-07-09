package com.example.trasuatuananh.base


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


open class CommonPresenter @JvmOverloads constructor(
    private val appBehaviorOnServiceError: AppBehaviorOnServiceError? = null,
    private val baseView: BaseView? = null,
    protected val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
) : BasePresenter {
    override fun subscribe() {

    }

    override fun unSubscribe() {

    }

    fun <T> baseCallApi(
        call: Call<T>,
        onSuccess: (T?) -> Unit,
        onError: (String) -> Unit
    ) {
        baseView?.showLoading()
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                baseView?.hideLoading()
                if (response.isSuccessful) {
                    onSuccess(response.body())
                } else {
                    val errorMessage = response.errorBody()?.string() ?: ""
                    onError(errorMessage)
                    appBehaviorOnServiceError?.handleServiceError(
                        BaseException(
                            code = response.code(),
                            message = errorMessage
                        )
                    )
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                baseView?.hideLoading()
                onError(t.message ?: "")
                appBehaviorOnServiceError?.handleServiceError(
                    BaseException(
                        code = -1,
                        message = t.message.orEmpty()
                    )
                )
            }
        })
    }
}