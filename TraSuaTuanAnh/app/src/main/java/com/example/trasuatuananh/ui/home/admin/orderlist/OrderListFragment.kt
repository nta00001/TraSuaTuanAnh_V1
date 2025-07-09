package com.example.trasuatuananh.ui.home.admin.orderlist

import android.widget.TextView
import com.example.trasuatuananh.R
import com.example.trasuatuananh.api.repository.food.FoodRepositoryImpl
import com.example.trasuatuananh.base.BaseDataBindFragment
import com.example.trasuatuananh.databinding.FragmentOrderListBinding
import com.example.trasuatuananh.ui.home.admin.adapter.OrderListAdapter
import com.example.trasuatuananh.ui.home.admin.orderlist.detail.OrderDetailFragment

class OrderListFragment :
    BaseDataBindFragment<FragmentOrderListBinding, OrderListContract.Presenter>(),
    OrderListContract.View {
    companion object {
        fun newInstance() = OrderListFragment()
    }

    private val adapter: OrderListAdapter by lazy {
        OrderListAdapter(
            onClickListener = { item, pos ->
                getBaseActivity().replaceFragment(
                    OrderDetailFragment.newInstance(item),
                    R.id.flMain
                )
            })
    }

    override fun getLayoutId() = R.layout.fragment_order_list
    override fun initView() {
        mBinding?.apply {

            rvListFood.adapter = adapter
            // Danh sách các TextView
            val textViews =
                listOf(txtConfirmOrder, txtShippingInProgress, txtCompleted, txtCanceled)

            // Hàm cục bộ để cập nhật background
            fun updateBackground(selectedTextView: TextView) {
                for (textView in textViews) {
                    if (textView == selectedTextView) {
                        textView.setBackgroundResource(R.drawable.bg_border_corner_lucky_money_view)
                    } else {
                        textView.setBackgroundResource(R.drawable.bg_button_disable)
                    }
                }
            }

            // Gán sự kiện click cho từng TextView
            txtConfirmOrder.setOnClickListener {
                mPresenter?.getListOrderAdmin(1)
                updateBackground(txtConfirmOrder)
            }

            txtShippingInProgress.setOnClickListener {
                mPresenter?.getListOrderAdmin(2)
                updateBackground(txtShippingInProgress)
            }

            txtCompleted.setOnClickListener {
                mPresenter?.getListOrderAdmin(3)
                updateBackground(txtCompleted)
            }

            txtCanceled.setOnClickListener {
                mPresenter?.getListOrderAdmin(0)
                updateBackground(txtCanceled)
            }
        }
    }


    override fun initData() {
        mPresenter = OrderListPresenter(
            this,
            FoodRepositoryImpl()
        ).apply {
            mBinding?.presenter = this
            getListOrderAdmin(1)

            listOrder().observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }
        }
    }
}