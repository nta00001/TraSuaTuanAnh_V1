package com.example.trasuatuananh.ui.home.promotion

import com.example.trasuatuananh.R
import com.example.trasuatuananh.api.repository.food.FoodRepositoryImpl
import com.example.trasuatuananh.base.BaseDataBindFragment
import com.example.trasuatuananh.databinding.FragmentDiscountBinding
import com.example.trasuatuananh.ui.home.promotion.detail.DiscountDetailFragment

class DiscountFragment :
    BaseDataBindFragment<FragmentDiscountBinding, DiscountContract.Presenter>(),
    DiscountContract.View {
    companion object {
        fun newInstance() = DiscountFragment()
    }

    override fun getLayoutId(): Int = R.layout.fragment_discount

    override fun initView() {
        mBinding?.apply {
            ctrlPromotion.setOnClickListener {
                getBaseActivity().addFragment(DiscountDetailFragment.newInstance(), R.id.flMain)

            }


        }
    }

    override fun initData() {
        mPresenter = DiscountPresenter(
            this,
            FoodRepositoryImpl()
        ).apply {
            mBinding?.presenter = this
            getListVoucherByUser()
        }
    }

    override fun getStringRes(id: Int): String {
        return getString(id)
    }
}