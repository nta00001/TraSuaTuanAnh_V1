package com.example.trasuatuananh.ui.home.admin.setting

import com.example.trasuatuananh.R
import com.example.trasuatuananh.base.BaseDataBindFragment
import com.example.trasuatuananh.databinding.FragmentSettingBinding

class SettingFragment : BaseDataBindFragment<FragmentSettingBinding, SettingContract.Presenter>(),
    SettingContract.View {
    companion object {
        fun newInstance() = SettingFragment()
    }

    override fun getLayoutId() = R.layout.fragment_setting

    override fun initView() {
        mBinding.apply { }
    }

    override fun initData() {
        mPresenter = SettingPresenter(this)
    }
}