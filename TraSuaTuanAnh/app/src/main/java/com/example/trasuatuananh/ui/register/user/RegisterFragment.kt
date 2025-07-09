package com.example.trasuatuananh.ui.register.user

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.trasuatuananh.R
import com.example.trasuatuananh.api.repository.account.AccountRepositoryImpl
import com.example.trasuatuananh.base.BaseDataBindFragment
import com.example.trasuatuananh.databinding.FragmentRegisterBinding
import com.example.trasuatuananh.util.CommonToast

class RegisterFragment :
    BaseDataBindFragment<FragmentRegisterBinding, RegisterContract.Presenter>(),
    RegisterContract.View {

    companion object {
        fun newInstance() = RegisterFragment()
    }

    override fun getLayoutId(): Int = R.layout.fragment_register

    override fun initView() {
        mBinding?.apply {
            toolbar.setOnBackClickListener {
                onBackClicked()
            }
        }

    }

    override fun initData() {
        mPresenter = RegisterPresenter(
            this,
            AccountRepositoryImpl()
        ).apply {
            mBinding?.presenter = this
        }
        mBinding?.view = this
    }


    override fun registerSuccessful() {
        CommonToast.showToast(
            requireContext(),
            getString(R.string.register_success),
            R.drawable.ic_checked_green
        )
        onBackClicked()
    }


    override fun openFragment(fragment: Fragment) {
        getBaseActivity().replaceFragment(fragment, R.id.flMain)
    }

    override fun showDiaLogInValid(message: String) {
        getBaseActivity().showAlertDialogNew(
            null,
            getString(R.string.app_notify_title),
            message,
            getString(R.string.common_close),
        )
    }

    override fun getStringRes(id: Int): String {
        return getString(id)
    }

    override fun getViewContext(): Context? {
        return context
    }

    override fun onBackClicked() {
        getBaseActivity().onBackFragment()
    }
}