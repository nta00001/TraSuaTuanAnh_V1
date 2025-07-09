package com.example.trasuatuananh.ui.home.search

import com.example.trasuatuananh.R
import com.example.trasuatuananh.base.BaseDataBindFragment
import com.example.trasuatuananh.databinding.FragmentSearchBinding

class SearchFragment :
    BaseDataBindFragment<FragmentSearchBinding, SearchContract.Presenter>(),
    SearchContract.View {
    companion object {
        fun newInstance() = SearchFragment()
    }

    override fun getLayoutId(): Int = R.layout.fragment_search

    override fun initView() {
        mBinding.apply { }
    }

    override fun initData() {
        mPresenter = SearchPresenter(this)
    }

}