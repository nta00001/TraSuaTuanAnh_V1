package com.example.trasuatuananh.ui.history

import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.trasuatuananh.R
import com.example.trasuatuananh.api.repository.order.OrderRepositoryImpl
import com.example.trasuatuananh.base.BaseDataBindFragment
import com.example.trasuatuananh.databinding.FragmentHistoryOrderBinding
import com.example.trasuatuananh.ui.history.adapter.HistoryAdapter
import com.example.trasuatuananh.ui.history.detail.HistoryOrderDetailFragment

class HistoryOrderFragment :
    BaseDataBindFragment<FragmentHistoryOrderBinding, HistoryOrderContract.Presenter>(),
    HistoryOrderContract.View {
    companion object {
        fun newInstance() = HistoryOrderFragment()
    }

    private val adapter: HistoryAdapter by lazy {
        HistoryAdapter(onItemClickListener = { orderId, position ->
            getBaseActivity().replaceFragment(
                HistoryOrderDetailFragment.newInstance(orderId),
                R.id.flMain
            )
        })
    }

    override fun getLayoutId() = R.layout.fragment_history_order

    override fun initView() {
        mBinding?.apply {
            toolbar.setOnBackClickListener {
                getBaseActivity().onBackFragment()
            }
            txtNewOrder.setOnClickListener {
                updateBackground(txtNewOrder)
                mPresenter?.setOrderStatus(setOf("0", "1"))
            }
            txtCompleted.setOnClickListener {
                updateBackground(txtCompleted)
                mPresenter?.setOrderStatus(setOf("2"))
            }
            txtCanceled.setOnClickListener {
                updateBackground(txtCanceled)
                mPresenter?.setOrderStatus(setOf("3"))
            }
            rvListFood.adapter = adapter

        }
        mBinding?.view = this

    }

    // Hàm cục bộ để cập nhật background
    private fun updateBackground(selectedTextView: TextView) {
        val textViews =
            listOf(mBinding?.txtNewOrder, mBinding?.txtCompleted, mBinding?.txtCanceled)
        // Gán sự kiện click cho từng TextView
        for (textView in textViews) {
            if (textView == selectedTextView) {
                textView.setBackgroundResource(R.drawable.bg_border_corner_lucky_money_view)
            } else {
                textView?.setBackgroundResource(R.drawable.bg_button_disable)
            }
        }
    }


    override fun initData() {
        mPresenter = HistoryOrderPresenter(this, OrderRepositoryImpl()).apply {
            getHistoryOrder()

            lifecycleScope.launchWhenStarted {
                filteredList().collect { list ->
                    adapter.submitList(list)
                }
            }

        }

    }
}