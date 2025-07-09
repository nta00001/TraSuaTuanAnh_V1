package com.example.trasuatuananh.ui.register.user

import androidx.lifecycle.MutableLiveData
import com.example.trasuatuananh.R
import com.example.trasuatuananh.api.repository.account.AccountRepository
import com.example.trasuatuananh.base.CommonPresenter
import com.example.trasuatuananh.util.StringUtils

class RegisterPresenter(
    private val view: RegisterContract.View,
    private val accountRepository: AccountRepository
) : CommonPresenter(view, view), RegisterContract.Presenter {

    private var _fullName = MutableLiveData("")
    private var _account = MutableLiveData("")
    private var _passWord = MutableLiveData("")

    override fun userName() = _fullName
    override fun account() = _account
    override fun password() = _passWord

    override fun register() {
        val validationResult = validateInputs()
        if (validationResult != null) {
            view.showDiaLogInValid(validationResult)
            return
        }

        baseCallApi(accountRepository.register(
            _account.value,
            _fullName.value,
            _passWord.value,
        ),
            onSuccess = { response ->
//                view.getViewContext()?.let {
//                    response?.data?.let { it1 ->
//                        TokenManager.saveToken(
//                            it,
//                            it1.token.toString()
//                        )
//                    }
//                }
                view.registerSuccessful()
            },
            onError = {

            })
    }

    private fun validateInputs(): String? {
        return when {
            !StringUtils.isValidLength(_fullName.value.orEmpty()) -> view.getStringRes(R.string.in_valid_full_name)
            !StringUtils.isValidPhoneNumber(_account.value.orEmpty()) -> view.getStringRes(R.string.in_valid_account)
            !StringUtils.isValidLength(_passWord.value.orEmpty()) -> view.getStringRes(R.string.in_valid_pass_word)
            else -> null
        }
    }
}
