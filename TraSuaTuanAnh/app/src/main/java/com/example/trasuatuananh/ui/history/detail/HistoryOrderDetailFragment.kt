package com.example.trasuatuananh.ui.history.detail

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.trasuatuananh.R
import com.example.trasuatuananh.api.repository.order.OrderRepositoryImpl
import com.example.trasuatuananh.base.BaseDataBindFragment
import com.example.trasuatuananh.databinding.FragmentHistoryOrderDetailBinding
import com.example.trasuatuananh.ui.history.adapter.OrderDetailAdapter

class HistoryOrderDetailFragment :
    BaseDataBindFragment<FragmentHistoryOrderDetailBinding, HistoryOrderDetailContract.Presenter>(),
    HistoryOrderDetailContract.View {

    companion object {
        private val ARG_ORDER_ID = "ARG_ORDER_ID"
        fun newInstance(
            orderId: String
        ): HistoryOrderDetailFragment {
            val fragment = HistoryOrderDetailFragment()
            val args = Bundle().apply {
                putString(ARG_ORDER_ID, orderId)
            }
            fragment.arguments = args
            return fragment
        }
    }

    private val orderId by lazy {
        arguments?.getString(ARG_ORDER_ID)
    }

    private val adapter: OrderDetailAdapter by lazy {
        OrderDetailAdapter()
    }


    override fun getLayoutId() = R.layout.fragment_history_order_detail


    override fun initView() {
        mBinding?.apply {
            toolbar.setOnBackClickListener {
                getBaseActivity().onBackFragment()
            }
            rvListFood.adapter = adapter
        }
    }

    override fun initData() {
        mPresenter = HistoryOrderDetailPresenter(
            this, OrderRepositoryImpl(),
            orderId ?: ""
        ).apply {
            getOrderDetail()
            mBinding?.presenter = this


            lifecycleScope.launchWhenStarted {
                oderDetail().collect { item ->
                    item?.foods?.let { adapter.setData(it) }
                    val (textResId, colorResId) = when (item?.orderStatus) {
                        "0" -> R.string.status_pending to R.color.text_brand
                        "1" -> R.string.status_shipping to R.color.orange_FFA928
                        "2" -> R.string.status_completed to R.color.black_alpha_text_24
                        "3" -> R.string.status_cancelled to R.color.red_text_FF4D4D
                        else -> null to null
                    }

                    textResId?.let {
                        mBinding?.txtStatus?.text = getString(it)
                    }

                    colorResId?.let {
                        mBinding?.txtStatus?.setTextColor(
                            ContextCompat.getColor(mBinding?.txtStatus?.context ?: return@let, it)
                        )
                    }

                }
            }

        }

    }
}