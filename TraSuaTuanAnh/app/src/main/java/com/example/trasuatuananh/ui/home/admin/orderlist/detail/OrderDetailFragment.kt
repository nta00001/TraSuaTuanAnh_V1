package com.example.trasuatuananh.ui.home.admin.orderlist.detail

import android.os.Bundle
import android.view.View
import com.example.trasuatuananh.R
import com.example.trasuatuananh.api.model.response.BillAdminResponse
import com.example.trasuatuananh.api.repository.food.FoodRepositoryImpl
import com.example.trasuatuananh.base.BaseDataBindFragment
import com.example.trasuatuananh.databinding.FragmentOrderDetailBinding
import com.example.trasuatuananh.ui.payment.adapter.PaymentAdapter
import com.example.trasuatuananh.util.CommonToast

class OrderDetailFragment :
    BaseDataBindFragment<FragmentOrderDetailBinding, OrderDetailContract.Presenter>(),
    OrderDetailContract.View {
    companion object {
        private const val ARG_ORDER = "ARG_ORDER"
        fun newInstance(
            order: BillAdminResponse
        ): OrderDetailFragment {
            val args = Bundle()
            args.putParcelable(ARG_ORDER, order)
            val fragment = OrderDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val order by lazy {
        arguments?.getParcelable<BillAdminResponse>(ARG_ORDER)
    }

    private val adapter: PaymentAdapter by lazy {
        PaymentAdapter()
    }


    override fun getLayoutId() = R.layout.fragment_order_detail

    override fun initView() {
        mBinding?.apply {
            if (order?.status == 2) {
                btnCancelOrder.visibility = View.GONE
                btnConfirmOrder.text = getString(R.string.order_status_completed)
            } else if (order?.status == 3 || order?.status == 0) {
                btnCancelOrder.visibility = View.GONE
                btnConfirmOrder.visibility = View.GONE
            }

            toolbar.setOnBackClickListener {
                getBaseActivity().onBackFragment()
            }
            btnConfirmOrder.setOnClickListener {
                order?.id?.let { it1 -> mPresenter?.confirmOrder(it1) }
            }
            btnCancelOrder.setOnClickListener {
                order?.let { it1 -> mPresenter?.cancelOrder(it1.id) }
            }
            rvListFood.adapter =adapter

        }
    }

    override fun initData() {
        mPresenter = OrderDetailPresenter(
            this,
            FoodRepositoryImpl(),
            order
        ).apply {
            mBinding?.presenter = this

            listFood().observe(viewLifecycleOwner) {
//                adapter.submitList(it)

            }
        }
    }

    override fun confirmSuccess(message: String) {
        CommonToast.showToast(
            requireContext(),
            message,
            R.drawable.ic_checked_green
        )
        getBaseActivity().onBackFragment()

    }

    override fun getStringRes(id: Int): String {
        return getString(id)
    }
}