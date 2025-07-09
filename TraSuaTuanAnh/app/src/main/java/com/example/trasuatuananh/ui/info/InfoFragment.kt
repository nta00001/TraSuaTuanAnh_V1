package com.example.trasuatuananh.ui.info

import com.example.trasuatuananh.R
import com.example.trasuatuananh.base.BaseDataBindFragment
import com.example.trasuatuananh.databinding.FragmentInfoBinding
import com.example.trasuatuananh.ui.history.HistoryOrderFragment

class InfoFragment : BaseDataBindFragment<FragmentInfoBinding, InfoContract.Presenter>(),
    InfoContract.View {

    companion object {
        fun newInstance() = InfoFragment()
    }

    override fun getLayoutId() = R.layout.fragment_info
    override fun initView() {
        mBinding?.apply {

            toolbar.setOnBackClickListener {
                getBaseActivity().onBackFragment()
            }
        }
        mBinding?.view = this
    }

    override fun initData() {
        mPresenter = InfoPresenter(this).apply {
            mBinding?.presenter = this
        }
    }

    override fun openFragmentHistoryOrder() {
        getBaseActivity().replaceFragment(HistoryOrderFragment.newInstance(), R.id.flMain)
    }
}